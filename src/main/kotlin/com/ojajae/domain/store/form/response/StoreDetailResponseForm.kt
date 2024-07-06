package com.ojajae.domain.store.form.response

import com.ojajae.common.DEFAULT_IMAGE_PATH
import com.ojajae.domain.item_tag.form.ItemTagStoreResponseForm
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

    val storeReadCount: Long,

    val imageUrls: List<String>,

    val storeFiles: List<StoreFileResponse> = emptyList(),

    val itemTags: List<ItemTagStoreResponseForm>? = emptyList(),
) {
    companion object {
        fun of(
            store: Store,
            storeReadCount: Long,
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
                storeReadCount = storeReadCount,
                itemTags = itemTags,
                imageUrls = imageUrls,
            )
        }
    }
}