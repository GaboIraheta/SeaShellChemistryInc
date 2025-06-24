package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

data class ChemElem (val name: String, val count: Int): FormulaItem {
    init { require (count >= 1) {"Assertion Error: Count must be a positive integer"} }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add(this.name)
    }

    override fun countElement(n: String): Long {
        return if (n == this.name) this.count.toLong() else 0
    }

    fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            append(name)
            if (count != 1) {
                withStyle(SpanStyle(
                    fontSize = 10.sp,
                    baselineShift = BaselineShift.Subscript
                )) {
                    append(count.toString())
                }
            }
        }
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