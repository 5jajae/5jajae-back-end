package com.ojajae.domain.item_tag.form

import com.ojajae.domain.item_tag.entity.ItemTag

data class ItemTagResponseForm(
    val id: Int,
    val name: String,
    val imageUrl: String,
) {
    companion object {
        fun of(
            itemTag: ItemTag,
            imageUrl: String,
        ): ItemTagResponseForm {
            return ItemTagResponseForm(
                id = itemTag.id!!,
                name = itemTag.name,
                imageUrl = imageUrl,
            )
        }
    }
}
