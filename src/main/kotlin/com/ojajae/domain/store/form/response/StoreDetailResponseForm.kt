package com.ojajae.domain.store.form.response

import com.ojajae.domain.item_tag.form.response.ItemTagStoreResponseForm
import com.ojajae.domain.store.entity.Store

data class StoreDetailResponseForm(
    val storeId: Int,

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

    val storeReadCount: Long,

    val thumbnailUrl: String?,

    val imageUrls: List<String>,

    val storeFiles: List<StoreFileResponse> = emptyList(),

    val itemTags: List<ItemTagStoreResponseForm>? = emptyList(),
) {
    companion object {
        fun of(
            store: Store,
            storeReadCount: Long,
            thumbnailUrl: String?,
            imageUrls: List<String> = emptyList(),
            itemTags: List<ItemTagStoreResponseForm> = emptyList(),
        ): StoreDetailResponseForm {
            return StoreDetailResponseForm(
                storeId = store.id!!,
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
                storeReadCount = storeReadCount,
                itemTags = itemTags,
                thumbnailUrl = thumbnailUrl,
                imageUrls = imageUrls,
            )
        }
    }
}