package com.ojajae.domain.store.service

import com.ojajae.common.entity.form.PageResponseForm
import com.ojajae.common.entity.form.Pagination
import com.ojajae.common.exception.BadRequestException
import com.ojajae.common.exception.NotFoundException
import com.ojajae.domain.item_tag.entity.ItemTagStore
import com.ojajae.domain.item_tag.form.request.StorePageRequestForm
import com.ojajae.domain.item_tag.service.ItemTagStoreAdminService
import com.ojajae.domain.store.exception.StoreException
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
) {
    @Transactional
    fun saveStore(requestForm: StoreSaveRequestForm) {
        checkForm(requestForm)

        val store = requestForm.toEntity()
        storeRepository.save(store)

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
