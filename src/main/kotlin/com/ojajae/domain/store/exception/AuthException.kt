package com.ojajae.domain.store.exception

import com.ojajae.common.exception.error.ErrorCode

enum class AuthException(
    override val message: String?,
): ErrorCode {
    BadCredential("아이디 또는 패스워드가 일치하지 않습니다."),
}