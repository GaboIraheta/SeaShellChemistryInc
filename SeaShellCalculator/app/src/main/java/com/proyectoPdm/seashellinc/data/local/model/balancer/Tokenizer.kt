package com.proyectoPdm.seashellinc.data.local.model.balancer

class Tokenizer(string: String){
    val str: String = string.replace("\u2212", "-")
    var pos: Int = 0

    init {
        skipSpaces()
    }

    fun peek(): String? {
        if (pos == str.length){
            return null
        }
        val match = "^([A-Za-z][a-z]*|[0-9]+|[+\\-^=()])".toRegex().find(str.substring(pos))
        return match?.value
    }

    fun take(): String? {
        if (pos == str.length){
            return null
        }

        val match = "^([A-Za-z][a-z]*|[0-9]+|[+\\-^=()])".toRegex().find(str.substring(pos))
        return if (match != null){
            val result = match.value
            pos += result.length
            skipSpaces()
            result
        }
        else {
            throw IllegalStateException("Símbolo inválido en posición $pos")
        }
    }

    fun consume(expectedToken: String) {
        val actualToken = take()

        if (actualToken != expectedToken) {
            throw IllegalStateException("Token mismatch: Se esperaba $expectedToken pero se obtuvo $actualToken")
        }
    }

    private fun skipSpaces(){
        val regex = "^[ \\t]*".toRegex()
        val matchResult = regex.find(str.substring(pos))

        if(matchResult != null){
            pos += matchResult.value.length
        }
        else {
            throw IllegalStateException("Assertion Error: No se encontró coincidencias para espacios al inicio.")
        }
    }
}