package com.ojajae.domain.store.controller

import com.ojajae.common.API_PREFIX
import com.ojajae.common.controller.BaseAPIController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_PREFIX/store")
class StoreAPIController(

): BaseAPIController() {
    @GetMapping
    fun test(): ResponseEntity<Nothing> {
        return okResponse()
    }
}
