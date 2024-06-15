package com.ojajae.common.web

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Response용 공통 클래스
 * 사용할지 말지..
 */
open class Content<T> protected constructor() {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    open var content: T? = null

    companion object {
        fun <T> of(result: T?): Content<T>? {
            if (result == null) return null

            return Content<T>().apply {
                this.content = result
            }
        }
    }
}
