package com.ojajae.domain.member.service

import com.ojajae.domain.member.entity.UserDetailsImpl
import com.ojajae.domain.member.form.request.LoginRequestForm
import com.ojajae.domain.member.form.response.LoginResponseForm
import com.ojajae.infra.config.security.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
) {
    fun login(form: LoginRequestForm): LoginResponseForm {
        return try {
            val authentication =
                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(form.username, form.password))
            SecurityContextHolder.getContext().authentication = authentication
            val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
            return LoginResponseForm(jwtUtil.generateToken(userDetails.username))
        } catch (e: BadCredentialsException) {
            e.printStackTrace()
            throw Exception("Invalid email/password supplied")
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Internal server error: " + e.message)
        }
    }
}

