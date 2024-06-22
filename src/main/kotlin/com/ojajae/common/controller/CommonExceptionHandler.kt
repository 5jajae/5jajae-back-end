package com.ojajae.common.controller

import com.ojajae.common.exception.BaseException
import com.ojajae.common.web.ExceptionResponseDTO
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice(assignableTypes = [BaseAPIController::class])
class CommonExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseExceptions(ex: BaseException, request: WebRequest): ResponseEntity<ExceptionResponseDTO> {
        val annotation = AnnotationUtils.findAnnotation(ex::class.java, ResponseStatus::class.java)
        val result = ExceptionResponseDTO()

        return if (annotation == null) {
            result.message = "알 수 없는 에러가 발생했습니다"

            ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(result)
        } else {
            result.message = ex.message ?: "알 수 없는 에러가 발생했습니다"

            ResponseEntity.status(annotation.code).contentType(MediaType.APPLICATION_JSON).body(result)
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ExceptionResponseDTO> {
        ex.printStackTrace()
        val result = ExceptionResponseDTO()

        result.message = "알 수 없는 에러가 발생했습니다"

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
    }
}