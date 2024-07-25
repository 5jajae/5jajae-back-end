package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.Store

data class StoreAdminDetailResponse(
    val id: Int,
    val name: String,
    val descriptions: String?,
    val address: String,
    val lat: Double,
    val lng: Double,
    val contactNumber: String?,
    val homepage: String?,
    val openingHours: String?,
    val representativeName: String?,
    val identificationNumber: String?,
    val items: String?,

    val itemTagIds: List<Int>,
    val storeFiles: List<StoreFileAdminResponse>,
) {
    companion object {
        fun of(
            store: Store,
            storeFiles: List<StoreFileAdminResponse>,
            itemTagStoreIds: List<Int>,
        ): StoreAdminDetailResponse {
            return StoreAdminDetailResponse(
                id = store.id!!,
                name = store.name,
                descriptions = store.descriptions,
                address = store.address,
                lat = store.lat,
                lng = store.lng,
                contactNumber = store.contactNumber,
                homepage = store.homepage,
                openingHours = store.openingHours,
                representativeName = store.representativeName,
                identificationNumber = store.identificationNumber,
                items = store.items,

                itemTagIds = itemTagStoreIds,
                storeFiles = storeFiles,
            )
        }
    }
}
