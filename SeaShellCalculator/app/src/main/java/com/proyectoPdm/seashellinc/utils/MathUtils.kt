package com.proyectoPdm.seashellinc.utils

import kotlin.math.abs

fun checkOverflow(x: Int): Int {
    if (abs(x) >= Int.MAX_VALUE) throw ArithmeticException("Arithmetic overflow")
    return x
}

fun checkedParsedLong(string: String): Int{
    val result = string.toIntOrNull()
    if (result == null) throw IllegalArgumentException("Not a number")
    return checkOverflow(result)
}

fun checkedAddSum(x: Int, y: Int): Int {
    return checkOverflow(x + y)
}

fun checkedMultiply(x: Int, y: Int): Int {
    return checkOverflow(x * y)
}

fun gcd(x: Int, y: Int): Int {
    var a = abs(x)
    var b = abs(y)

    while (b != 0) {
        val c = a % b
        a = b
        b = c
    }
    return a
}