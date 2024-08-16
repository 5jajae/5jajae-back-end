package com.ojajae.domain.store.entity

import com.ojajae.common.entity.ImmutableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class StoreFile(
    var storeId: Int,

    var fileUrl: String,

    var sort: Int = 0,
) : ImmutableEntity<Int>()
