package com.ojajae.domain.store.controller

import com.ojajae.common.API_PREFIX
import com.ojajae.common.controller.BaseAPIController
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.store.form.request.StoreListRequestForm
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.form.response.StoreListResponseForm
import com.ojajae.domain.store.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_PREFIX/stores")
class StoreAPIController(
    private val storeService: StoreService
): BaseAPIController() {
    @GetMapping
    fun getStoreList(
        @ModelAttribute request: StoreListRequestForm,
    ): ResponseEntity<ResultDTO<StoreListResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = storeService.getStoreList(
                request = request,
            ),
        ))
    }

    @GetMapping("/{storeId}")
    fun getStore(@PathVariable storeId: Int): ResponseEntity<ResultDTO<StoreDetailResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = storeService.getStore(storeId))
        )
    }
}
