package com.ojajae.domain.store.controller

import com.ojajae.common.API_PREFIX
import com.ojajae.common.controller.BaseAPIController
import com.ojajae.common.utils.WebTool
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.dashbord.service.DashboardService
import com.ojajae.domain.store.form.request.StoreFavoritesRequestForm
import com.ojajae.domain.store.form.request.StoreListRequestForm
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.form.response.StoreFavoritesResponseForm
import com.ojajae.domain.store.form.response.StoreListResponseForm
import com.ojajae.domain.store.service.StoreService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_PREFIX/stores")
class StoreAPIController(
    private val storeService: StoreService,
    private val dashboardService: DashboardService,
): BaseAPIController() {
    @GetMapping
    fun getStoreList(
        @ModelAttribute requestForm: StoreListRequestForm,
        request: HttpServletRequest,
    ): ResponseEntity<ResultDTO<StoreListResponseForm>> {
        val data = storeService.getStoreList(
            request = requestForm,
        )

        WebTool.getClientIP(request)?.let {
            dashboardService.readStoreList(clientIP = it)
        }

        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = data,
        ))
    }

    @GetMapping("/{storeId}")
    fun getStore(@PathVariable storeId: Int): ResponseEntity<ResultDTO<StoreDetailResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = storeService.getStore(storeId))
        )
    }


    @PostMapping("/favorites")
    fun getFavorites(@RequestBody storeFavoritesRequestForm: StoreFavoritesRequestForm): ResponseEntity<ResultDTO<StoreFavoritesResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = storeService.getFavorites(storeFavoritesRequestForm))
        )
    }
}
