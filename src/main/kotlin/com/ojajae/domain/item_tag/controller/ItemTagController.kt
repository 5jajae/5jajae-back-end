package com.ojajae.domain.item_tag.controller

import com.ojajae.common.API_PREFIX
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.item_tag.form.ItemTagListResponseForm
import com.ojajae.domain.item_tag.service.ItemTagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_PREFIX/item-tags")
class ItemTagController(
    private val itemTageService: ItemTagService,
) {
    @GetMapping
    fun getItemTagList(): ResponseEntity<ResultDTO<ItemTagListResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess("", ItemTagListResponseForm(
            itemTags = itemTageService.getItemTagList(),
        )))
    }
}
