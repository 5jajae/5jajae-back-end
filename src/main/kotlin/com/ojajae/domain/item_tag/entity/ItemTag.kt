package com.ojajae.domain.item_tag.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Entity

@Entity
class ItemTag(
    var name: String,

    var imageUrl: String,
): MutableEntity<Int>() {
    fun update(param: ItemTag) {
        this.name = param.name
        this.imageUrl = param.imageUrl
    }
}
