package com.ojajae.domain.store.form.request

import com.ojajae.domain.store.entity.Store

data class StoreSaveRequestForm(
    val name: String = "",
    val descriptions: String? = null,
    val address: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val contactNumber: String? = null,
    val homepage: String? = null,
    val openingHours: String? = null,
    val representativeName: String? = null,
    val identificationNumber: String? = null,
    val items: String? = null,
    val isConstruction: Boolean? = null,

    val imageUrls: List<String>? = null,
    val itemTagIds: List<Int> = emptyList(),
) {
    fun toEntity(): Store {
        return Store(
            name = name,
            descriptions = descriptions,
            address = address,
            lat = lat,
            lng = lng,
            contactNumber = contactNumber,
            homepage = homepage,
            openingHours = openingHours,
            representativeName = representativeName,
            identificationNumber = identificationNumber,
            items = items,
            isConstruction = isConstruction,
        )
    }
}
