package com.ojajae.domain.item_tag.service

import com.ojajae.domain.item_tag.form.ItemTagResponseForm
import com.ojajae.domain.item_tag.repository.ItemTagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemTagService(
    private val itemTagRepository: ItemTagRepository,
) {
    @Transactional(readOnly = true)
    fun getItemTagList(): List<ItemTagResponseForm> {
        return itemTagRepository.findAll().map {
            ItemTagResponseForm.of(it)
        }
    }
}
