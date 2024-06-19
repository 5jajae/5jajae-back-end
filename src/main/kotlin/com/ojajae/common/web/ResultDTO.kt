package com.ojajae.common.web

class ResultDTO<T>(
    val status: String,
    val message: String,
    val data: T? = null,
) {
    companion object{
        private val SUCCESS_STATUS = "success"
        private val FAIL_STATUS = "fail"
        private val ERROR_STATUS = "error"
        fun <T>createSuccess(message: String = "", data: T?): ResultDTO<T> {
            return ResultDTO(SUCCESS_STATUS, message, data)
        }

        fun <T>createFail(message: String = ""): ResultDTO<T> {
            return ResultDTO(FAIL_STATUS, message)
        }

        fun <T>createError(message: String = ""): ResultDTO<T> {
            return ResultDTO(ERROR_STATUS, message)
        }
    }

    constructor(message: String): this("SUCCESS", message)
}