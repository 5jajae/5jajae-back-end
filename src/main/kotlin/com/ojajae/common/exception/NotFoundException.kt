package com.ojajae.common.exception

import com.ojajae.common.exception.error.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "NotFound")
class NotFoundException: BaseException {
    constructor(): super()

    constructor(
        errorCode: ErrorCode,
    ): super(
        httpStatus = HttpStatus.NOT_FOUND,
        errorCode = errorCode,
    )
}
