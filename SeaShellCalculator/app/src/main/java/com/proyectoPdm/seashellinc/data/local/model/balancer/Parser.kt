package com.proyectoPdm.seashellinc.data.local.model.balancer

import com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes.ChemElem
import com.proyectoPdm.seashellinc.utils.checkedParsedLong

class Parser(formulaString: String) {
    private val token = Tokenizer(formulaString)

    fun parseElement(): ChemElem {
        val name = token.take() ?: throw IllegalStateException("Se esperaba nombre de elemento, pero se obtuvo null.")

        if (!name.matches("^[A-Z][a-z]*$".toRegex())) {
            throw IllegalStateException("Assertion Error: Se esperaba nombre de elemento con formato [A-Z][a-z]*, pero se obtuvo '$name'")
        }
        val count = parseOptionalNumber()
        return ChemElem(name, count)
    }

    fun parseOptionalNumber(): Int {
        val next = token.peek()
        return if (next != null && next.matches("^[0-9]+$".toRegex())){
            checkedParsedLong(token.take()!!, token.pos).toInt()
        } else 1
    }
}