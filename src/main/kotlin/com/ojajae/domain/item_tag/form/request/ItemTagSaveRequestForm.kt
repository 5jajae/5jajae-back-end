package com.ojajae.domain.item_tag.form.request

import com.ojajae.domain.item_tag.entity.ItemTag
import org.springframework.web.multipart.MultipartFile

data class ItemTagSaveRequestForm(
    val name: String = "",
    val image: MultipartFile? = null,
) {
    fun toEntity(imageUrl: String): ItemTag {
        return ItemTag(
            name = name,
            imageUrl = imageUrl,
        )
    }
}
