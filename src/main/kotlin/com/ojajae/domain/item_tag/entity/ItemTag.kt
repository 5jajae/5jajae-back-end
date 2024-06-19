package com.ojajae.domain.item_tag.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Entity

@Entity
class ItemTag(
    var name: String,

    var imageUrl: String,
): MutableEntity<Long>()
