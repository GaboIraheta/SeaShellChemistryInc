package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes.FormulaItem

data class ChemElem (val name: String, val count: Int): FormulaItem {
    init {
        if (count < 1) {
            throw IllegalStateException("Assertion Error: Count debe ser un entero positivo, pero se obtuvo $count para el elemento $name")
        }
    }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add(name)
    }

    override fun countElement(targetName: String): Long {
        return if (targetName == name) this.count.toLong() else 0
    }

    fun toDisplayString(): String {
        return if (count == 1) name else "$name$count"
    }

// toHtml() equivalent for Jetpack Compose:
// Instead of returning an HTML DOM element, in Compose you would
// typically build an AnnotatedString for rich text display (subscripts).
// This logic would likely reside in a Composable function that takes ChemElem as input.
//
//    fun toAnnotatedString(): AnnotatedString {
//        return buildAnnotatedString {
//            append(name)
//            if (count != 1) {
//                withStyle(SpanStyle(baselineShift = BaselineShift.Subscript)) {
//                    append(count.toString())
//                }
//            }
//        }
//    }

}