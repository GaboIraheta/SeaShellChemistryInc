package com.proyectoPdm.seashellinc.utils

import com.proyectoPdm.seashellinc.data.local.model.balancer.ParseError
import kotlin.math.abs

private const val INT_MAX = 9_007_199_254_740_992L // this is 2^53

/**
 * Parsea la cadena de entrada a un Long y verifica que no exceda el límite definido.
 * @param str La cadena a parsear.
 * @param errorPos La posición del error para el ParseError.
 * @return El valor Long parseado y verificado.
 * @throws ParseError Si la cadena no es un número válido o si excede el límite.
 */
fun checkedParsedLong(string: String, errorPos: Int): Long{
    var result = string.toLongOrNull() ?: throw ParseError("No es un número válido", errorPos)

    if (result > INT_MAX || result < -INT_MAX){
        throw ParseError("Arithmetic Overflow: El número es demasiado grande", errorPos)
    }
    return result
}

/**
 * Suma dos números Long y verifica que el resultado no exceda el límite definido.
 * @param x El primer número.
 * @param y El segundo número.
 * @param errorPos La posición del error para el ParseError (opcional, si quieres indicar dónde ocurrió el desbordamiento).
 * @return La suma verificada.
 * @throws ParseError Si la suma excede el límite.
 */
fun checkedAddSum(x: Long, y: Long, errorPos: Int? = null): Long {
    val sum = x + y
    if (sum > INT_MAX || sum < -INT_MAX) {
        throw ParseError("Arithmetic Overflow: Suma es demasiado grande", errorPos ?: -1)
    }
    return sum
}

/**
 * Multiplica dos números Long y verifica que el resultado no exceda el límite definido.
 * @param x El primer número.
 * @param y El segundo número.
 * @param errorPos La posición del error para el ParseError (opcional).
 * @return El producto verificado.
 * @throws ParseError Si el producto excede el límite.
 */
fun checkedMultiply(x: Long, y: Long, errorPos: Int? = null): Long {
    val product = x * y
    if (product > INT_MAX || product < -INT_MAX) {
        throw ParseError("Artimethic Overflow: Producto es demasiado grande", errorPos ?: -1)
    }
    return product
}

/**
 * Calcula el máximo común divisor (GCD) de dos enteros.
 * @param x El primer entero.
 * @param y El segundo entero.
 * @return El máximo común divisor.
 */
fun gcd(x: Long, y: Long): Long {
    var a = abs(x)
    var b = abs(y)

    while (b != 0L) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}