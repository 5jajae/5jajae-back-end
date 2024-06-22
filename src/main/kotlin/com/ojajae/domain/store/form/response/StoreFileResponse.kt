package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.StoreFile

data class StoreFileResponse(
    val id: Int,
    val storeId: Int,
    var imageUrl: String,
) {
    companion object {
        fun of(
            storeFile: StoreFile,
            imageUrl: String,
        ): StoreFileResponse {
            return StoreFileResponse(
                id = storeFile.id!!,
                storeId = storeFile.storeId,
                imageUrl = imageUrl,
            )
        }
    }
}
