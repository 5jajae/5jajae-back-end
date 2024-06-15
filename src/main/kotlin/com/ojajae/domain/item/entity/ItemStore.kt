package com.ojajae.domain.item.entity

import com.ojajae.common.entity.ImmutableEntity
import jakarta.persistence.Entity

@Entity
class ItemStore(
    var storeId: Long,

    var itemId: Long,
) : ImmutableEntity<Long>()
