package com.ojajae.domain.store.service

import com.ojajae.common.entity.form.PageResponseForm
import com.ojajae.common.entity.form.Pagination
import com.ojajae.common.exception.BadRequestException
import com.ojajae.common.exception.NotFoundException
import com.ojajae.common.utils.generateRandomString
import com.ojajae.domain.item_tag.entity.ItemTagStore
import com.ojajae.domain.item_tag.form.request.StorePageRequestForm
import com.ojajae.domain.item_tag.service.ItemTagStoreAdminService
import com.ojajae.domain.s3.S3Service
import com.ojajae.domain.store.entity.StoreFile
import com.ojajae.domain.store.exception.StoreException
import com.ojajae.domain.store.form.request.StoreSaveRequestForm
import com.ojajae.domain.store.form.response.StoreAdminDetailResponse
import com.ojajae.domain.store.form.response.StoreAdminPageResponseForm
import com.ojajae.domain.store.repository.StoreRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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

        if (!requestForm.images.isNullOrEmpty()) {
            val images = requestForm.images.mapIndexedNotNull { idx, file ->
                val extension = file.originalFilename?.let {
                    FilenameUtils.getExtension(it)
                } ?: "jpg"
                val fileUrl = generateFileUrl(extension)

                if (s3Service.uploadFile(path = fileUrl, file = file)) {
                    StoreFile(
                        storeId = store.id!!,
                        fileUrl = fileUrl,
                        order = idx + 1,
                    )
                } else {
                    null
                }
            }

            if (images.isNotEmpty()) {
                storeFileAdminService.saveAllStoreFiles(files = images)
            }
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
            content = page.content.map {
                StoreAdminPageResponseForm.of(store = it)
            },
            pagination = Pagination(page)
        )
    }

    fun getStoreById(storeId: Int): StoreAdminDetailResponse {
        val store = storeRepository.findByIdOrNull(id = storeId) ?: throw NotFoundException(StoreException.NotFoundStore)
        val itemTagStores = itemTagStoreAdminService.findByStoreId(storeId = storeId)
        val storeFiles = storeFileAdminService.findImagesByStoreId(storeId = storeId)

        return StoreAdminDetailResponse.of(
            store = store,
            storeFiles = storeFiles,
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

        // file image 업로드 추가(기존꺼 다 삭제하고 업로드 할건지, 구분할건지 필요)

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

    private fun generateFileUrl(extension: String): String {
        val now = LocalDate.now()

        return "ojajae/upload/store/${now.year}/${now.monthValue}/${generateRandomString()}.${extension}"
    }
}
