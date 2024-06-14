package com.ojajae.common.web

class ResultDTO(
    val status: String,
    val message: String,
    val data: MutableMap<String, Any> = mutableMapOf()
) {
    companion object{
        private val SUCCESS_STATUS = "success"
        private val FAIL_STATUS = "fail"
        private val ERROR_STATUS = "error"
        fun createSuccess(message: String = ""): ResultDTO {
            return ResultDTO(SUCCESS_STATUS, message)
        }

        fun createFail(message: String = ""): ResultDTO {
            return ResultDTO(FAIL_STATUS, message)
        }

        fun createError(message: String = ""): ResultDTO {
            return ResultDTO(ERROR_STATUS, message)
        }
    }

    constructor(message: String): this("SUCCESS", message)

    fun put(key: String, value: Any) {
        data[key] = value
    }
}