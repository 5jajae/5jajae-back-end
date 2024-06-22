package com.ojajae.common.exception

import com.ojajae.common.exception.error.ErrorCode
import org.springframework.http.HttpStatus

open class BaseException : RuntimeException {
    var httpStatus: HttpStatus? = null
    var errorCode: ErrorCode? = null

    constructor() : super()

    constructor(
        httpStatus: HttpStatus,
        errorCode: ErrorCode,
    ) : super(errorCode.message) {
        this.httpStatus = httpStatus
        this.errorCode = errorCode
    }
}
