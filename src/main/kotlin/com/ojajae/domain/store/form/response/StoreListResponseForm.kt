package com.ojajae.domain.store.form.response

import com.ojajae.common.DEFAULT_IMAGE_PATH
import com.ojajae.domain.item_tag.form.ItemTagStoreResponseForm
import com.ojajae.domain.store.entity.Store

data class StoreListResponseForm(
    val stores: List<StoreListResponse> = emptyList(),
)

data class StoreListResponse(
    val id: Int,
    val name: String,
    val descriptions: String?,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnailImage: String? = DEFAULT_IMAGE_PATH,
    val itemTags: List<ItemTagStoreResponseForm>? = emptyList(),
) {
    companion object {
        fun of(
            store: Store,
            thumbnailImage: String? = null,
            itemTags: List<ItemTagStoreResponseForm> = emptyList(),
        ): StoreListResponse {
            return StoreListResponse(
                id = store.id!!,
                name = store.name,
                descriptions = store.descriptions,
                address = store.address,
                lat = store.lat,
                lng = store.lng,
                thumbnailImage = thumbnailImage,
                itemTags = itemTags,
            )
        }
    }
}
