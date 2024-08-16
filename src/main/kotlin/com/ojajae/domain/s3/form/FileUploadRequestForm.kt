package com.ojajae.domain.s3.form

import org.springframework.web.multipart.MultipartFile

data class FileUploadRequestForm (
    val file: MultipartFile? = null,
)
