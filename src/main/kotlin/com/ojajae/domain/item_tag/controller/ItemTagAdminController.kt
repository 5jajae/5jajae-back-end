package com.ojajae.domain.item_tag.controller

import com.ojajae.common.ADMIN_API_PREFIX
import com.ojajae.common.controller.BaseAdminAPIController
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.item_tag.form.request.ItemTagSaveRequestForm
import com.ojajae.domain.item_tag.form.response.ItemTagAdminListResponseForm
import com.ojajae.domain.item_tag.service.ItemTagAdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$ADMIN_API_PREFIX/item-tags")
class ItemTagAdminController(
    private val itemTagAdminService: ItemTagAdminService,
) : BaseAdminAPIController() {
    @PostMapping
    fun saveItemTag(
        @RequestBody requestForm: ItemTagSaveRequestForm,
    ): ResponseEntity<Nothing> {
        itemTagAdminService.saveItemTag(requestForm = requestForm)

        return okResponse()
    }

    @GetMapping
    fun getItemTagPage(): ResponseEntity<ResultDTO<List<ItemTagAdminListResponseForm>>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(message = "", data = itemTagAdminService.getItemTagList()))
    }

    @PutMapping("/{itemTagId}")
    fun updateItemTag(
        @PathVariable itemTagId: Int,
        @RequestBody requestForm: ItemTagSaveRequestForm,
    ): ResponseEntity<Nothing> {
        itemTagAdminService.updateItemTag(
            itemTagId = itemTagId,
            requestForm = requestForm,
        )

        return okResponse()
    }
}
