package com.ojajae.common.entity.form

open class PageResponseForm<T>(
    val data: List<T>,
    val pagination: Pagination,
)
