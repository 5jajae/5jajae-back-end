package com.ojajae.infra.config.security

import com.ojajae.common.ACCESS_TOKEN
import com.ojajae.domain.member.service.UserDetailsServiceImpl
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*


@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var username: String? = null
        val token = getToken(request)
        if (token != null) {
            try {
                username = jwtUtil.getUsername(token)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: ExpiredJwtException) {
                e.printStackTrace()
            }
        }
        if (username != null && Objects.isNull(SecurityContextHolder.getContext().authentication)) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
            if (Objects.nonNull(userDetails)) {
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authentication.setDetails(WebAuthenticationDetailsSource().buildDetails(request))
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val cookies: Array<Cookie>? = request.cookies
        val cookie = cookies?.find { it.name.equals(ACCESS_TOKEN) }
        val jwtToken = cookie?.value
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else if(jwtToken != null) {
            jwtToken
        } else {
            null
        }
    }
}