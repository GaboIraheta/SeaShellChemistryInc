package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

interface FormulaItem {
    fun getElements(resultSet: MutableSet<String>)
    fun countElement(targetName: String): Long // Usa Long porque los conteos pueden crecer
}