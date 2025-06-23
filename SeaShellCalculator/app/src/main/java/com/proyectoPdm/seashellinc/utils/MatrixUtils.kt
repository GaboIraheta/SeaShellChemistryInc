package com.proyectoPdm.seashellinc.utils

import com.proyectoPdm.seashellinc.data.local.model.balancer.Matrix
import com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes.Equation

fun buildMatrix(eqn: Equation): Matrix {
    var elems = eqn.getElements()
    var lhs = eqn.leftSide
    var rhs = eqn.rightSide
    var matrix = Matrix(elems.size + 1, lhs.size + rhs.size + 1)
    elems.forEachIndexed { i, elem ->
        var j = 0
        for (term in lhs) {
            matrix.set(i, j, term.countElement(elem).toInt())
            j++
        }
        for (term in rhs) {
            matrix.set(i, j, -term.countElement(elem).toInt())
            j++
        }
    }
    return matrix
}

fun solve (matrix: Matrix) {
    matrix.gaussJordanEliminate()

    fun countNonzeroCoeffs(row: Int): Int {
        var count = 0
        for (i in 0 until matrix.numCols) {
            if (matrix.get(row, i) != 0) count++
        }
        return count
    }

    var i: Int = 0
    for (j in 0 until matrix.numRows - 1) {
        i = j
        if (countNonzeroCoeffs(j) > 1) break
    }
    if (i == matrix.numRows - 1) throw IllegalStateException("All-zero solutions")

    matrix.set(matrix.numRows - 1, i, 1)
    matrix.set(matrix.numRows -1, matrix.numCols - 1, 1)
    matrix.gaussJordanEliminate()
}

fun extractCoefficients(matrix: Matrix): IntArray {
    val cols = matrix.numCols
    val rows = matrix.numRows

    if (cols - 1 > rows || matrix.get(cols - 2, cols - 2) == 0) {
        throw IllegalStateException("Multiple independent solutions")
    }
    var lcm = 1;
    for (i in 0 until cols - 1) {
        lcm = checkedMultiply(
            (lcm / gcd(lcm.toLong(), matrix.get(i, i).toLong()).toInt()).toLong(),
            matrix.get(i, i).toLong()
        ).toInt()
    }

    var coefs = mutableListOf<Int>()
    for (i in 0 until cols - 1) coefs.add(
        checkedMultiply(
            (lcm / matrix.get(i, i)).toLong(),
            matrix.get(i, cols - i).toLong()
        ).toInt()
    )
    if (coefs.all { x -> x == 0 }) throw IllegalStateException("Assertion error: All-zero solution")
    return coefs.toIntArray()
}

fun checkAnswer(eqn: Equation, coefs: IntArray) {
    if (coefs.size != eqn.leftSide.size + eqn.rightSide.size) throw IllegalStateException("Assertion error: Mismatched length")
    if (coefs.all { x -> x == 0 }) throw IllegalStateException("Assertion error: All-zero solution")

    for (elem in eqn.getElements()){
        var sum = 0
        var j = 0
        for (term in eqn.leftSide){
            sum = checkedAddSum(sum.toLong(),
                checkedMultiply(
                    term.countElement(elem).toLong(),
                    coefs[j].toLong()
                )
            ).toInt()
            j++
        }
        for (term in eqn.rightSide) {
            sum = checkedAddSum(sum.toLong(),
                checkedMultiply(
                    term.countElement(elem).toLong(),
                    -coefs[j].toLong()
                )
            ).toInt()
            j++
        }
        if (sum != 0) throw IllegalStateException("Assertion error: Incorrect balance")
    }
}