package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import com.proyectoPdm.seashellinc.utils.checkedAddSum

data class Term(
    val items: List<FormulaItem>,
    val charge: Int
): FormulaItem {
    init { if (items.isEmpty() && charge != -1) throw IllegalArgumentException("Invalid term") }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add("e")
        for (item in items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(name: String): Long {
        return if (name == "e") {
            return -this.charge.toLong()
        }
        else {
            var sum = 0L
            for (item in this.items) {
                sum = checkedAddSum(sum, item.countElement(name))
            }
            return sum
        }
    }

    fun toDisplayString(): String {
        val innerString = items.joinToString(separator = ""){
            when (it) {
                is ChemElem -> it.toDisplayString()
                is Group -> it.toDisplayString()
                else -> ""
            }
        }

        if (this.items.isEmpty() && charge == -1) {
            return "e⁻"
        }

        val chargeString = when {
            charge == 0 -> ""
            charge == 1 -> "+"
            charge == -1 -> "⁻"
            charge > 0 -> "$charge+"
            else -> "${-charge}⁻"
        }

        return "$innerString$chargeString"
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