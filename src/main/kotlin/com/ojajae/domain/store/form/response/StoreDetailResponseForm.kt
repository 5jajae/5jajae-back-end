package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.Store

data class StoreDetailResponseForm(
    val storeId: Long,

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
) {
    companion object {
        fun of(item: Store): StoreDetailResponseForm {
            return StoreDetailResponseForm(
                storeId = item.id!!,
                name = item.name,
                descriptions = item.descriptions,
                address = item.address,
                lat = item.lat,
                lng = item.lng,
                contactNumber = item.contactNumber,
                homepage = item.homepage,
                openingHours = item.openingHours,
                representativeName = item.representativeName,
                identificationNumber = item.identificationNumber,
                items = item.items,
            )
        }
    }
}