package com.ojajae.common.entity.form

open class PageResponseForm<T>(
    val content: List<T>,
    val pagination: Pagination,
)
