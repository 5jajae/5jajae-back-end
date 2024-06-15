package com.ojajae.common.controller

import org.springframework.http.ResponseEntity

open class BaseAPIController {
    fun okResponse(): ResponseEntity<Nothing> {
        return ResponseEntity.ok().build()
    }
}
