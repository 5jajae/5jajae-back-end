package com.ojajae.domain.item_tag.service

import com.ojajae.domain.item_tag.form.ItemTagStoreResponseForm
import com.ojajae.domain.item_tag.repository.ItemTagStoreRepository
import org.springframework.stereotype.Service

@Service
class ItemTagStoreService(
    private val itemTagStoreRepository: ItemTagStoreRepository,
) {
    fun findByStoreId(storeId: Int): List<ItemTagStoreResponseForm> {
        return findByStoreIdIn(listOf(storeId))
    }

    fun findByStoreIdIn(storeIds: List<Int>): List<ItemTagStoreResponseForm> {
        return itemTagStoreRepository.findByStoreIdIn(storeIds = storeIds).map { ItemTagStoreResponseForm.of(it) }
    }
}
