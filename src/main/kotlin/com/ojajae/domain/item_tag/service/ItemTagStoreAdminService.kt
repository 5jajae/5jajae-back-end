package com.ojajae.domain.item_tag.service

import com.ojajae.domain.item_tag.entity.ItemTagStore
import com.ojajae.domain.item_tag.form.response.ItemTagStoreResponseForm
import com.ojajae.domain.item_tag.repository.ItemTagStoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemTagStoreAdminService(
    private val itemTagStoreRepository: ItemTagStoreRepository,
) {
    @Transactional
    fun saveItemTagStores(itemTagStores: List<ItemTagStore>) {
        itemTagStoreRepository.saveAll(itemTagStores)
    }

    fun findByStoreId(storeId: Int): List<ItemTagStoreResponseForm> {
        return itemTagStoreRepository.findByStoreId(storeId = storeId)
            .map {
                ItemTagStoreResponseForm.of(itemTagStoreVO = it)
            }
    }

    @Transactional
    fun deleteByItemTagStoreByStoreId(storeId: Int) {
        itemTagStoreRepository.deleteItemTagStoresByStoreId(storeId = storeId)
    }
}
