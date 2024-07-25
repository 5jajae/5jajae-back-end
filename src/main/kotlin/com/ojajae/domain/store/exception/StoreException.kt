package com.ojajae.domain.store.exception

import com.ojajae.common.exception.error.ErrorCode

enum class StoreException(
    override val message: String?,
) : ErrorCode {
    NotFoundStore("업체를 찾을 수 없습니다."),
    ItemTagIdsIsEmpty("아이템 태그는 필수 값입니다."),
}
