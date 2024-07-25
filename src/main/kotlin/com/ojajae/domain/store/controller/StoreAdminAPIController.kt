package com.ojajae.domain.store.controller

import com.ojajae.common.ADMIN_API_PREFIX
import com.ojajae.common.controller.BaseAdminAPIController
import com.ojajae.common.entity.form.PageResponseForm
import com.ojajae.domain.store.form.request.StorePageRequestForm
import com.ojajae.domain.store.form.request.StoreSaveRequestForm
import com.ojajae.domain.store.form.response.StoreAdminDetailResponse
import com.ojajae.domain.store.form.response.StoreAdminPageResponseForm
import com.ojajae.domain.store.service.StoreAdminService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$ADMIN_API_PREFIX/stores")
class StoreAdminAPIController(
    private val storeAdminService: StoreAdminService,
) : BaseAdminAPIController() {
    @PostMapping
    fun saveStore(
        @RequestBody requestForm: StoreSaveRequestForm,
    ): ResponseEntity<Nothing> {
        storeAdminService.saveStore(requestForm = requestForm)

        return okResponse()
    }

    @GetMapping
    fun getStorePage(
        requestForm: StorePageRequestForm,
        pageable: Pageable,
    ): ResponseEntity<PageResponseForm<StoreAdminPageResponseForm>> {
        return ResponseEntity.ok(storeAdminService.getStorePage(
            requestForm = requestForm,
            pageable = pageable,
        ))
    }

    @GetMapping("/{storeId}")
    fun getStoreDetail(
        @PathVariable storeId: Int,
    ): ResponseEntity<StoreAdminDetailResponse> {
        return ResponseEntity.ok(
            storeAdminService.getStoreById(storeId = storeId)
        )
    }

    @PutMapping("/{storeId}")
    fun updateStore(
        @PathVariable storeId: Int,
        @RequestBody requestForm: StoreSaveRequestForm,
    ): ResponseEntity<Nothing> {
        storeAdminService.updateStore(
            storeId = storeId,
            requestForm = requestForm,
        )

        return okResponse()
    }
}
