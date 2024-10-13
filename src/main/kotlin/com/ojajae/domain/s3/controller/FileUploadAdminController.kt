package com.ojajae.domain.s3.controller

import com.ojajae.common.ADMIN_API_PREFIX
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.s3.form.FileUploadRequestForm
import com.ojajae.domain.s3.form.FileUploadResponseForm
import com.ojajae.domain.s3.service.S3UploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$ADMIN_API_PREFIX/upload")
class FileUploadAdminController(
    private val s3UploadService: S3UploadService,
) {
    @PostMapping("/stores")
    fun storeUploadFile(
        @ModelAttribute requestForm: FileUploadRequestForm,
    ):ResponseEntity<ResultDTO<FileUploadResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = s3UploadService.uploadFile(
                prefix = "stores",
                requestForm = requestForm,
            ),
        ))
    }

    @PostMapping("/storeThumbnail")
    fun storeUploadThumbnailFile(
        @ModelAttribute requestForm: FileUploadRequestForm,
    ):ResponseEntity<ResultDTO<FileUploadResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = s3UploadService.uploadFile(
                prefix = "thumbnail",
                requestForm = requestForm,
            ),
        ))
    }

    @PostMapping("/item-tags")
    fun itemTageUploadFile(
        @ModelAttribute requestForm: FileUploadRequestForm,
    ):ResponseEntity<ResultDTO<FileUploadResponseForm>> {
        return ResponseEntity.ok(ResultDTO.createSuccess(
            message = "",
            data = s3UploadService.uploadFile(
                prefix = "item-tags",
                requestForm = requestForm,
            ),
        ))
    }
}
