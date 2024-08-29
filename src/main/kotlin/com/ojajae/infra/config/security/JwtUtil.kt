package com.ojajae.infra.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function


@Component
class JwtUtil {
    @Value("\${jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${jwt.expiration-time}")
    private val expirationTime: Long? = null
    fun getClaims(token: String?): Claims {
        val secretKey = Keys.hmacShaKeyFor(secretKey!!.toByteArray(StandardCharsets.UTF_8))
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body
    }

    fun <T> getClaim(
        token: String?,
        claimsResolver: Function<Claims, T>
    ): T {
        val claims = getClaims(token)
        return claimsResolver.apply(claims)
    }

    fun getUsername(token: String?): String {
        return getClaim(token) { obj: Claims -> obj.subject }
    }

    fun getExpirationDate(token: String?): Date {
        return getClaim(token) { obj: Claims -> obj.expiration }
    }

    fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDate(token)
        return expiration.before(Date())
    }

    fun generateToken(username: String?): String {
        val secretKey = Keys.hmacShaKeyFor(secretKey!!.toByteArray(StandardCharsets.UTF_8))
        return Jwts.builder().setSubject(username).setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime!!))
            .claim("email", username).signWith(secretKey).compact()
    }
}

