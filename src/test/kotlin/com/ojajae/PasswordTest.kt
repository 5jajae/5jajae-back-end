package com.ojajae

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class PasswordTest @Autowired constructor(
    private val passwordEncoder: PasswordEncoder
) {
    @Test
    fun passwordGenerateTest() {
        println(passwordEncoder.encode("12345"))
    }
}