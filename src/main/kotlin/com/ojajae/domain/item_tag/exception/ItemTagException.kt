package com.ojajae.domain.item_tag.exception

import com.ojajae.common.exception.error.ErrorCode

enum class ItemTagException(
    override val message: String?,
) : ErrorCode {
    ItemTagFileIsEmpty("이미지 파일을 선택해주세요"),
}
