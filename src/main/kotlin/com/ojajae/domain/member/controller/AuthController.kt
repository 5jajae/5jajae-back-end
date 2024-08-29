package com.ojajae.domain.member.controller

import com.ojajae.common.ADMIN_API_PREFIX
import com.ojajae.domain.member.form.request.LoginRequestForm
import com.ojajae.domain.member.form.response.LoginResponseForm
import com.ojajae.domain.member.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("$ADMIN_API_PREFIX/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    @ResponseBody
    fun login(
        @RequestBody form: LoginRequestForm
    ): ResponseEntity<LoginResponseForm> {
        return ResponseEntity.ok(authService.login(form))
    }
}

