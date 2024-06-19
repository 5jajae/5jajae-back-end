package com.ojajae.domain.store.controller

import com.ojajae.common.API_PREFIX
import com.ojajae.common.controller.BaseAPIController
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_PREFIX/store")
class StoreAPIController(
    val storeService: StoreService
): BaseAPIController() {
    @GetMapping
    fun test(): ResponseEntity<Nothing> {
        return okResponse()
    }

    @GetMapping("/{storeId}")
    fun getStore(@PathVariable storeId: Int): ResponseEntity<ResultDTO<StoreDetailResponseForm>> {
        return ResponseEntity.ok(storeService.getStore(storeId))
    }
}
