package com.ojajae.common.exception

import com.ojajae.common.exception.error.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BadCredential")
class BadCredentialException: BaseException {
    constructor(
        errorCode: ErrorCode,
    ): super(
        httpStatus = HttpStatus.NOT_FOUND,
        errorCode = errorCode,
    )
}