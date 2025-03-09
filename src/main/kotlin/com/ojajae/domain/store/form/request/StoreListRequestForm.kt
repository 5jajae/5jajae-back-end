package com.ojajae.domain.store.form.request

import com.ojajae.domain.store.constant.MyCoordinate
import com.ojajae.domain.store.constant.StoreListSortType
import com.ojajae.domain.store.repository.search.StoreSearch

data class StoreListRequestForm(
    val address: String? = null,
    val itemTagIds: List<Int>? = null,
    val lat: Double? = MyCoordinate.DEFAULT_LAT,
    val lng: Double? = MyCoordinate.DEFAULT_LNG,
    val sort: StoreListSortType = StoreListSortType.LATEST,
) {
    fun toStoreSearch(): StoreSearch {
        return StoreSearch(
            address = address,
            itemTagIds = itemTagIds,
            lat = lat,
            lng = lng,
            sort = sort
        )
    }
}
