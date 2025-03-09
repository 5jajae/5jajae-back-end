package com.ojajae.domain.store.repository.search

import com.ojajae.domain.store.constant.StoreListSortType

data class StoreSearch (
    val address: String? = null,
    val itemTagIds: List<Int>? = null,
    val storeIdList : List<Long>? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val sort: StoreListSortType = StoreListSortType.LATEST,
)
