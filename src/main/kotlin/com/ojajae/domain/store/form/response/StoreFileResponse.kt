package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.StoreFile

data class StoreFileResponse(
    val id: Int,
    val storeId: Int,
    val imageUrl: String,
) {
    companion object {
        fun of(storeFile: StoreFile): StoreFileResponse {
            return StoreFileResponse(
                id = storeFile.id!!,
                storeId = storeFile.storeId,
                imageUrl = storeFile.fileUrl,
            )
        }
    }
}
