package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes.FormulaItem
import com.proyectoPdm.seashellinc.data.local.model.balancer.ParseError
import com.proyectoPdm.seashellinc.utils.checkedAddSum

data class Term(val items: List<FormulaItem>): FormulaItem {
    init {
        if (items.isEmpty()) {
            throw ParseError("Término vacío", -1)
        }
    }

    override fun getElements(resultSet: MutableSet<String>) {
        for (item in items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(targetName: String): Long {
        var sum: Long = 0
        for (item in items){
            sum = checkedAddSum(sum, item.countElement(targetName))
        }
        return sum
    }

    fun toDisplayString(): String {
        return items.joinToString(separator = ""){
            when(it){
                is ChemElem -> it.toDisplayString()
                is Group -> it.toDisplayString()
                else -> ""
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