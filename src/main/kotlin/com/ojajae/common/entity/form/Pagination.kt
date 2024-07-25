package com.ojajae.common.entity.form

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

data class Pagination(
    var totalElements: Long = 0,
    var totalPages: Int = 0,
    var currentPage: Int = 0,
    var size: Int = 0,
    var last: Boolean = false
) {
    constructor(page: Page<*>) : this() {
        totalElements = page.totalElements
        totalPages = page.totalPages
        currentPage = page.number
        last = page.isLast

        val unpaged = Pageable.unpaged()

        size = if (page.pageable == unpaged) {
            0
        } else {
            page.pageable.pageSize
        }

    }

}
