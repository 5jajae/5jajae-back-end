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
    val isConstruction: Boolean?,

    val itemTagIds: List<Int>,
    val imageUrls: List<StoreImageAdminResponse>,
) {
    companion object {
        fun of(
            store: Store,
            storeFiles: List<StoreImageAdminResponse>,
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
                isConstruction = store.isConstruction,

                itemTagIds = itemTagStoreIds,
                imageUrls = storeFiles,
            )
        }
    }
}
