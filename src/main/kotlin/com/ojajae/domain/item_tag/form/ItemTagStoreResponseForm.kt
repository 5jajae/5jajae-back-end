package com.ojajae.domain.item_tag.form

import com.ojajae.domain.item_tag.vo.ItemTagStoreVO

data class ItemTagStoreResponseForm(
    val id: Int,
    val storeId: Int,
    val name: String,
) {
    companion object {
        fun of(itemTagStoreVO: ItemTagStoreVO): ItemTagStoreResponseForm {
            return ItemTagStoreResponseForm(
                id = itemTagStoreVO.id,
                storeId = itemTagStoreVO.storeId,
                name = itemTagStoreVO.name,
            )
        }
    }
}
