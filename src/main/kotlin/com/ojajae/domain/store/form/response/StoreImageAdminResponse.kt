package com.ojajae.domain.store.form.response

import com.ojajae.domain.store.entity.StoreFile

data class StoreImageAdminResponse(
    val id: Int,
    val storeId: Int,
    val fileKey: String,
    var imageUrl: String,
) {
    companion object {
        fun of(
            storeFile: StoreFile,
            imageUrl: String,
        ): StoreImageAdminResponse {
            return StoreImageAdminResponse(
                id = storeFile.id!!,
                storeId = storeFile.storeId,
                fileKey = storeFile.fileUrl,
                imageUrl = imageUrl,
            )
        }
    }
}
