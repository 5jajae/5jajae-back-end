package com.ojajae.common.exception

import com.ojajae.common.exception.error.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BadRequest")
class BadRequestException: BaseException {
    constructor(): super()

    constructor(
        errorCode: ErrorCode,
    ): super(
        httpStatus = HttpStatus.BAD_REQUEST,
        errorCode = errorCode,
    )
}
