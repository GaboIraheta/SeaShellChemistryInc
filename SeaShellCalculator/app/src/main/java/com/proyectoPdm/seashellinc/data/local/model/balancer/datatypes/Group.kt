package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.utils.checkedAddSum
import com.proyectoPdm.seashellinc.utils.checkedMultiply

data class Group(
    private val items: List<FormulaItem>,
    private val count: Int
) : FormulaItem {

    init {
        require(count >= 1) { "Assertion Error: Count must be a positive integer" }
    }

    override fun getElements(resultSet: MutableSet<String>) {
        for (item in this.items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(name: String): Long {
        var sum: Long = 0
        for (item in this.items) {
            sum = checkedAddSum(sum, checkedMultiply(item.countElement(name), this.count.toLong()))
        }
        return sum
    }

    fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            append("(")
            for (item in items) {
                when (item) {
                    is ChemElem -> append(item.toAnnotatedString())
                    is Group -> append(item.toAnnotatedString())
                    else -> Unit
                }
            }
            append(")")
            if (count != 1) {
                withStyle(
                    SpanStyle(
                        fontSize = 10.sp,
                        baselineShift = BaselineShift.Subscript
                    )
                ) {
                    append(count.toString())
                }
            }
        }
    }

// toHtml() equivalente para Jetpack Compose:
// Similar a ChemElem, la lógica de renderizado de AnnotatedString residiría en un Composable.
//    fun toAnnotatedString(): AnnotatedString {
//        return buildAnnotatedString {
//            append("(")
//            for (item in items) {
//                append(
//                    when (item) {
//                        is ChemElem -> item.toAnnotatedString()
//                        is Group -> item.toAnnotatedString()
//                        else -> AnnotatedString("")
//                    }
//                )
//            }
//            append(")")
//            if (count != 1) {
//                withStyle(SpanStyle(baselineShift = BaselineShift.Subscript)) {
//                    append(count.toString())
//                }
//            }
//        }
//    }

}