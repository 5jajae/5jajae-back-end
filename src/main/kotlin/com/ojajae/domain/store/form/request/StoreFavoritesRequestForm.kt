package com.ojajae.domain.store.form.request

import com.ojajae.domain.store.constant.MyCoordinate
import com.ojajae.domain.store.repository.search.StoreSearch

data class StoreFavoritesRequestForm (
    val storeIdList : List<Long>,
    val lat: Double? = MyCoordinate.DEFAULT_LAT,
    val lng: Double? = MyCoordinate.DEFAULT_LNG
) {
    fun toStoreSearch() : StoreSearch {
        return StoreSearch(
            storeIdList = storeIdList,
            lat = lat,
            lng = lng
        )
    }
}