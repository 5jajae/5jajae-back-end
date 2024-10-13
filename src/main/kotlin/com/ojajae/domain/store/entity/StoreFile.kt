package com.ojajae.domain.store.entity

import com.ojajae.common.entity.ImmutableEntity
import com.ojajae.domain.store.constant.StoreFileType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class StoreFile(
    var storeId: Int,

    var fileUrl: String,

    var sort: Int = 0,

    @Enumerated(EnumType.STRING)
    var fileType: StoreFileType
) : ImmutableEntity<Int>()
