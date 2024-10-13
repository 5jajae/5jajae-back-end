package com.ojajae.domain.store.service

import com.ojajae.common.entity.form.PageResponseForm
import com.ojajae.common.entity.form.Pagination
import com.ojajae.common.exception.BadRequestException
import com.ojajae.common.exception.NotFoundException
import com.ojajae.domain.item_tag.entity.ItemTagStore
import com.ojajae.domain.item_tag.service.ItemTagStoreAdminService
import com.ojajae.domain.s3.service.S3Service
import com.ojajae.domain.store.constant.StoreFileType
import com.ojajae.domain.store.entity.StoreFile
import com.ojajae.domain.store.exception.StoreException
import com.ojajae.domain.store.form.request.StorePageRequestForm
import com.ojajae.domain.store.form.request.StoreSaveRequestForm
import com.ojajae.domain.store.form.response.StoreAdminDetailResponse
import com.ojajae.domain.store.form.response.StoreAdminPageResponseForm
import com.ojajae.domain.store.repository.StoreRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreAdminService(
    private val itemTagStoreAdminService: ItemTagStoreAdminService,
    private val storeFileAdminService: StoreFileAdminService,

    private val storeRepository: StoreRepository,

    private val s3Service: S3Service,
) {
    @Transactional
    fun saveStore(requestForm: StoreSaveRequestForm) {
        checkForm(requestForm)

        val store = requestForm.toEntity()
        storeRepository.save(store)

        if (!requestForm.imageUrls.isNullOrEmpty()) {
            val images = requestForm.imageUrls.mapIndexed { idx, imageUrl ->
                StoreFile(
                    storeId = store.id!!,
                    fileUrl = imageUrl,
                    sort = idx + 1,
                    fileType = StoreFileType.MAIN_IMAGE
                )
            }

            if (images.isNotEmpty()) {
                storeFileAdminService.saveAllStoreFiles(files = images)
            }
        }

        if (!requestForm.thumbnailImageUrl.isNullOrEmpty()) {
            val image = StoreFile(
                storeId = store.id!!,
                fileUrl = requestForm.thumbnailImageUrl,
                sort = 0,
                fileType = StoreFileType.THUMBNAIL_IMAGE,
            )

            storeFileAdminService.saveAllStoreFiles(files = listOf(image))
        }

        itemTagStoreAdminService.saveItemTagStores(requestForm.itemTagIds.map {
            ItemTagStore(
                itemTagId = it,
                storeId = store.id!!
            )
        })
    }

    fun getStorePage(
        requestForm: StorePageRequestForm,
        pageable: Pageable,
    ): PageResponseForm<StoreAdminPageResponseForm> {
        val page = storeRepository.getStorePage(
            requestForm = requestForm,
            pageable = pageable,
        )

        return PageResponseForm(
            data = page.content.map {
                StoreAdminPageResponseForm.of(store = it)
            },
            pagination = Pagination(page)
        )
    }

    fun getStoreById(storeId: Int): StoreAdminDetailResponse {
        val store = storeRepository.findByIdOrNull(id = storeId)
            ?: throw NotFoundException(StoreException.NotFoundStore)
        val itemTagStores = itemTagStoreAdminService.findByStoreId(storeId = storeId)
        val storeFiles = storeFileAdminService.findImagesByStoreId(storeId = storeId, StoreFileType.MAIN_IMAGE)
        val storeThumbnailFile = storeFileAdminService.findThumbnailImageByStoreId(storeId = storeId)

        return StoreAdminDetailResponse.of(
            store = store,
            storeFiles = storeFiles,
            thumbnailImage = storeThumbnailFile,
            itemTagStoreIds = itemTagStores.map { it.id },
        )
    }

    @Transactional
    fun updateStore(
        storeId: Int,
        requestForm: StoreSaveRequestForm,
    ) {
        checkForm(requestForm)

        val store = storeRepository.findOneWithWriteOrNullLockById(id = storeId)
            ?: throw NotFoundException(StoreException.NotFoundStore)

        store.update(param = requestForm.toEntity())

        val prevImages = storeFileAdminService.findImagesByStoreIdForUpdate(storeId = storeId)

        // 상점 메인 이미지 삭제 및 추가
        if (prevImages.isNotEmpty() || !requestForm.imageUrls.isNullOrEmpty() || !requestForm.thumbnailImageUrl.isNullOrEmpty()) {
            val deleteTarget = prevImages.filter {
                (it.fileType == StoreFileType.MAIN_IMAGE && (requestForm.imageUrls?.contains(it.fileUrl) == false))
                ||
                (it.fileType == StoreFileType.THUMBNAIL_IMAGE && it.fileUrl != requestForm.thumbnailImageUrl)
            }

            if (deleteTarget.isNotEmpty()) {
                deleteTarget.forEach { s3Service.deleteFile(it.fileUrl) }

                storeFileAdminService.deleteByStoreIdAndImageUrls(storeId = storeId, imageUrls = deleteTarget.map { it.fileUrl })
            }

            val prevImageUrls = prevImages.filter{ it.fileType == StoreFileType.MAIN_IMAGE }.map { it.fileUrl }
            val prevThumbnailImage = prevImages.find { it.fileType == StoreFileType.THUMBNAIL_IMAGE && it.fileUrl == requestForm.thumbnailImageUrl }
            val saveTarget = requestForm.imageUrls?.filter { it !in prevImageUrls }

            if (!saveTarget.isNullOrEmpty()) {
                storeFileAdminService.saveAllStoreFiles(saveTarget.mapIndexed { idx, imageUrl ->
                    StoreFile(
                        storeId = store.id!!,
                        fileUrl = imageUrl,
                        sort = idx + 1,
                        fileType = StoreFileType.MAIN_IMAGE
                    )
                })
            }

            if (prevThumbnailImage == null && !requestForm.thumbnailImageUrl.isNullOrEmpty()) {
                storeFileAdminService.saveAllStoreFiles(listOf(StoreFile(
                    storeId = store.id!!,
                    fileUrl = requestForm.thumbnailImageUrl,
                    sort = 1,
                    fileType = StoreFileType.THUMBNAIL_IMAGE
                )))
            }
        }

        itemTagStoreAdminService.deleteByItemTagStoreByStoreId(storeId = storeId)
        itemTagStoreAdminService.saveItemTagStores(requestForm.itemTagIds.map {
            ItemTagStore(
                itemTagId = it,
                storeId = storeId,
            )
        })
    }

    private fun checkForm(requestForm: StoreSaveRequestForm) {
        if (requestForm.itemTagIds.isEmpty()) {
            throw BadRequestException(StoreException.ItemTagIdsIsEmpty)
        }
    }
}
