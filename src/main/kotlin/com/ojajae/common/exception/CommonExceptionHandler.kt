package com.ojajae.common.exception

import com.ojajae.common.web.ResultDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CommonExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleException(ex: CustomException): ResponseEntity<ResultDTO> {
        ex.printStackTrace()
        val result: ResultDTO = ResultDTO.createError(ex.message?: "알 수 없는 에러가 발생하였습니다.")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ResultDTO> {
        ex.printStackTrace()
        val result: ResultDTO = ResultDTO.createError("알 수 없는 에러가 발생하였습니다.")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
    }
}