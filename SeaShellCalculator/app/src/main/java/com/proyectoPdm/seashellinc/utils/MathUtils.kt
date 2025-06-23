package com.proyectoPdm.seashellinc.utils

import kotlin.math.abs

fun checkOverflow(x: Long): Long {
    if (abs(x) >= Long.MAX_VALUE) throw ArithmeticException("Arithmetic overflow")
    return x
}

fun checkedParsedLong(string: String): Long{
    val result = string.toLongOrNull()
    if (result == null) throw IllegalArgumentException("Not a number")
    return checkOverflow(result)
}

fun checkedAddSum(x: Long, y: Long): Long {
    return checkOverflow(x + y)
}

fun checkedMultiply(x: Long, y: Long): Long {
    return checkOverflow(x * y)
}

fun gcd(x: Long, y: Long): Long {
    var a = abs(x)
    var b = abs(y)

    while (b != 0L) {
        val c = a % b
        a = b
        b = c
    }
    return a
}