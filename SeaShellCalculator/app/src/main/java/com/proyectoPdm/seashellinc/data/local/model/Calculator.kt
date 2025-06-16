package com.proyectoPdm.seashellinc.data.local.model

object Calculator {
    // Funciones para masa sobre masa y volumen sobre volumen
    fun calculateSolute(solvent: Double, concentration: Double): Double{
        return ((concentration/100) * solvent) / (1 - (concentration/100))
    }

    fun calculateSolvent(solute: Double, concentration: Double): Double{
        return (solute - ((concentration/100) * solute)) / (concentration/100)
    }

    fun calculateConcentration(solute: Double, solvent: Double): Double{
        return (solute / (solvent + solute)) * 100
    }

    // Funciones para masa sobre volumen y para partes por millón (asumiendo mg y litros)
    fun calculateSoluteVar (solvent: Double, concentration: Double): Double{
        return ((concentration/100) * solvent)
    }

    fun calculateSolventVar (solute: Double, concentration: Double): Double{
        return (solute / (concentration/100))
    }

    fun calculateConcentrationVar (solute: Double, solvent: Double): Double{
        return (solute / solvent) * 100
    }

    // this is so stupid
    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}