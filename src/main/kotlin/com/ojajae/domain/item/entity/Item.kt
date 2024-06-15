package com.ojajae.domain.item.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Entity

@Entity
class Item(
    var name: String,

    var imageUrl: String,
): MutableEntity<Long>()
