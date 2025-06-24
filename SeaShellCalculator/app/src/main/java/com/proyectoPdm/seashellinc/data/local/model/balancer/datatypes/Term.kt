package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.utils.checkedAddSum
import kotlin.math.abs

data class Term(
    val items: List<FormulaItem>,
    val charge: Int
) : FormulaItem {
    init {
        if (items.isEmpty() && charge != -1) throw IllegalArgumentException("Invalid term")
    }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add("e")
        for (item in items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(name: String): Long {
        return if (name == "e") {
            return -this.charge.toLong()
        } else {
            var sum = 0L
            for (item in this.items) {
                sum = checkedAddSum(sum, item.countElement(name))
            }
            return sum
        }
    }

    fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            if (items.isEmpty() && charge == -1) {
                append("e")
                withStyle(SpanStyle(
                    fontSize = 10.sp,
                    baselineShift = BaselineShift.Superscript
                )){ append("-") }
            }
            else {
                for (item in items) {
                    when (item) {
                        is ChemElem -> append(item.toAnnotatedString())
                        is Group -> append(item.toAnnotatedString())
                        else -> Unit
                    }
                }

                if (charge != 0) {
                    var s = if (abs(charge) == 1) "" else abs(charge).toString()
                    s += if (charge > 0) "+" else "-"
                    withStyle(SpanStyle(
                        fontSize = 10.sp,
                        baselineShift = BaselineShift.Superscript
                    )) { append(s) }
                }
            }
        }
    }

// toHtml() equivalente para Jetpack Compose:
// Similar a ChemElem y Group, la lógica de renderizado de AnnotatedString residiría en un Composable.
//    fun toAnnotatedString(): AnnotatedString {
//        return buildAnnotatedString {
//            for (item in items) {
//                append(
//                    when (item) {
//                        is ChemElem -> item.toAnnotatedString()
//                        is Group -> item.toAnnotatedString()
//                        else -> AnnotatedString("")
//                    }
//                )
//            }
//        }
//    }

}