package com.ojajae.domain.member.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull


@Entity
class Member(
    val username: @NotNull String,
    val password: @NotNull String,
    val role: @NotNull String,
): MutableEntity<Int>() {
}

