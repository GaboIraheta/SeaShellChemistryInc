package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

data class Equation(
    val leftSide: List<Term>,
    val rightSide: List<Term>
) {
    fun getElements(): List<String> {
        val resultSet = mutableSetOf<String>()
        (this.leftSide + this.rightSide).forEach { item ->
            item.getElements(resultSet)
        }
        return resultSet.toList()
    }

    fun toAnnotatedString(coefs: List<Int>? = null): AnnotatedString {
        val minus = "\u2212"
        val rightArrow = "\u2192"

        if (coefs != null && coefs.size != this.leftSide.size + this.rightSide.size) {
            throw IllegalStateException("Mismatched number of coefficients")
        }

        return buildAnnotatedString {
            var j = 0
            fun termsToAnnotatedString(terms: List<Term>) {
                var head = true
                for (term in terms) {
                    var coef = if (coefs != null) coefs[j] else 1
                    if (coef != 0) {
                        if (head) head = false
                        else append(" + ")
                        if (coef != 1) {
                            var coefString = coef.toString().replace(Regex("-"), minus)
                            if (coef < 0) { // Coeficientes negativos no es bueno, pero es una respuesta válida
                                withStyle(SpanStyle(color = Color.Red)) {
                                    append(coefString)
                                }
                            }
                            else append(coefString)
                        }
                        append(term.toAnnotatedString())
                    }
                    j++
                }
            }
            termsToAnnotatedString(this@Equation.leftSide)
            append(rightArrow)
            termsToAnnotatedString(this@Equation.rightSide)
        }
    }

// --- Rendering for Jetpack Compose (Modern Android UI) ---
// Instead of `toHtml()`, in Compose you would create a Composable function
// that takes an Equation object (and optional coefficients) and builds the UI.
// This allows for rich text (subscripts, superscripts, colors, etc.) using AnnotatedString.
//    @Composable
//    fun EquationComposable(
//        equation: Equation,
//        coefs: List<Long>? = null,
//        // You might pass additional styles or modifiers
//    ) {
//        val combinedTerms = equation.leftSide + equation.rightSide
//        if (coefs != null && coefs.size != combinedTerms.size) {
//            throw IllegalArgumentException("Mismatched number of coefficients for Composable")
//        }
//
//        var coefIndex = 0 // To track current coefficient
//
//        // Helper Composable for terms to avoid duplication
//        @Composable
//        fun TermsDisplay(terms: List<Term>) {
//            var isFirstTerm = true
//            for (term in terms) {
//                val currentCoef = coefs?.get(coefIndex) ?: 1L
//
//                if (currentCoef != 0L) {
//                    if (!isFirstTerm) {
//                        // Display plus sign between terms
//                        Text(text = " + ", style = MaterialTheme.typography.bodyLarge) // Example styling
//                    } else {
//                        isFirstTerm = false
//                    }
//
//                    if (currentCoef != 1L) {
//                        // Display coefficient
//                        Text(
//                            text = currentCoef.toString(),
//                            style = MaterialTheme.typography.bodyLarge, // Example styling
//                            modifier = Modifier.padding(end = 4.dp) // Example spacing
//                        )
//                    }
//                    // Display the term itself (you'd have a similar toAnnotatedString or Composable for Term)
//                    // For demonstration, let's assume Term has a toAnnotatedString()
//                    Text(text = term.toAnnotatedString(), style = MaterialTheme.typography.bodyLarge)
//                }
//                coefIndex++
//            }
//        }
//
//        // Layout the equation horizontally
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            TermsDisplay(equation.leftSide)
//            // Display reaction arrow
//            Text(text = " \u2192 ", style = MaterialTheme.typography.bodyLarge) // Unicode arrow
//            TermsDisplay(equation.rightSide)
//        }
//    }
}