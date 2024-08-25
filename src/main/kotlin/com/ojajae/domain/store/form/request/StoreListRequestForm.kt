package com.ojajae.domain.store.form.request

data class StoreListRequestForm(
    val address: String? = null,
    val itemTagIds: List<Int>? = null,
    val lat: Double? = 37.56668901266339,
    val lng: Double? = 126.97842874643777,
    val sort: StoreListSortType = StoreListSortType.LATEST,
)

enum class StoreListSortType {
    LATEST,
    DISTANCE,
}
