package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

data class ChemElem (val name: String, val count: Int): FormulaItem {
    init { require (count >= 1) {"Assertion Error: Count must be a positive integer"} }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add(this.name)
    }

    override fun countElement(n: String): Long {
        return if (n == this.name) this.count.toLong() else 0
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