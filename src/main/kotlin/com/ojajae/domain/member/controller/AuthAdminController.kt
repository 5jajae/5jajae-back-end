package com.ojajae.domain.member.controller

import com.ojajae.common.ADMIN_API_PREFIX
import com.ojajae.common.controller.BaseAdminAPIController
import com.ojajae.domain.member.form.request.LoginRequestForm
import com.ojajae.domain.member.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("$ADMIN_API_PREFIX/auth")
class AuthAdminController(
    private val authService: AuthService
): BaseAdminAPIController() {
    @PostMapping("/login")
    @ResponseBody
    fun login(
        @RequestBody form: LoginRequestForm,
        response: HttpServletResponse
    ): ResponseEntity<Nothing> {
        val loginResponseForm = authService.login(form)
        val cookie = ResponseCookie.from("jwtToken", loginResponseForm.jwtToken)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build()
        response.setHeader("Set-Cookie", cookie.toString())
        return okResponse()
    }
}

