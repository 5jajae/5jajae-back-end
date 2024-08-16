package com.ojajae.domain.s3.exception

import com.ojajae.common.exception.error.ErrorCode

enum class UploadException (
    override val message: String?,
) : ErrorCode {
    FileIsEmpty("파일을 선택해주세요."),
}
