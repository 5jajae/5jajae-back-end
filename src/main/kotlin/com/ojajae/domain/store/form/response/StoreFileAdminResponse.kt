package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.StoreFile

data class StoreFileAdminResponse(
    val id: Int,
    val storeId: Int,
    var imageUrl: String,
) {
    companion object {
        fun of(
            storeFile: StoreFile,
            imageUrl: String,
        ): StoreFileAdminResponse {
            return StoreFileAdminResponse(
                id = storeFile.id!!,
                storeId = storeFile.storeId,
                imageUrl = imageUrl,
            )
        }
    }
}
