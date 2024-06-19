package com.ojajae.domain.item_tag.entity

import com.ojajae.common.entity.ImmutableEntity
import jakarta.persistence.Entity

@Entity
class ItemTagStore(
    var itemTagId: Int,

    var storeId: Int,
) : ImmutableEntity<Int>()
