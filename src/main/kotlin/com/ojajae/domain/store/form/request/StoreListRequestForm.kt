package com.ojajae.domain.store.form.request

data class StoreListRequestForm(
    val address: String? = null,
    val itemTagIds: List<Int>? = null,
)
