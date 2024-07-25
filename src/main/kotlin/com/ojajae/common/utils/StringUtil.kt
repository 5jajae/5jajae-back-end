package com.ojajae.common.utils

import kotlin.random.Random

const val ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

fun generateRandomString(size: Int = 20): String {
    return (1..size)
        .map { Random.nextInt(0, ALPHA_NUMERIC.length) }
        .map(ALPHA_NUMERIC::get)
        .joinToString("")
}
