package com.ojajae.domain.item_tag.service

import com.ojajae.domain.item_tag.form.response.ItemTagResponseForm
import com.ojajae.domain.item_tag.repository.ItemTagRepository
import com.ojajae.domain.s3.S3Service
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemTagService(
    private val itemTagRepository: ItemTagRepository,
    private val s3Service: S3Service,
) {
    @Transactional(readOnly = true)
    fun getItemTagList(): List<ItemTagResponseForm> {
        return itemTagRepository.findAll().map {
            ItemTagResponseForm.of(
                itemTag = it,
                imageUrl = s3Service.getPresignedUrl(it.imageUrl),
            )
        }
    }
}
