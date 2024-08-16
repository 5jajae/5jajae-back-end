package com.ojajae.domain.item_tag.form.response

import com.ojajae.domain.item_tag.entity.ItemTag

data class ItemTagAdminListResponseForm(
    val id: Int,
    val name: String,
    var imageUrl: String = "",
) {
    companion object {
        fun of(
            itemTag: ItemTag,
        ): ItemTagAdminListResponseForm {
            return ItemTagAdminListResponseForm(
                id = itemTag.id!!,
                name = itemTag.name,
            )
        }
    }
}
