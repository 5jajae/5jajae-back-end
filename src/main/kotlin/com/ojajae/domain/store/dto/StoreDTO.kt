package com.ojajae.domain.store.dto

import com.ojajae.domain.store.entity.Store

data class StoreDTO(
    val id: Long,
    val name: String,
) {
    companion object {
        fun of(item: Store): StoreDTO {
            return StoreDTO(
                id = item.id!!,
                name = item.name,
            )
        }
    }
}
