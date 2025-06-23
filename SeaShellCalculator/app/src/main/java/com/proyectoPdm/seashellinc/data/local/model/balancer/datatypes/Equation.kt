package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

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

    fun toDisplayString(coefs: List<Long>? = null): String {
        if (coefs != null && coefs.size != this.leftSide.size + this.rightSide.size) {
            throw IllegalStateException("Número de coeficientes no coincide")
        }

        val node = StringBuilder()
        var coefIndex = 0

        fun appendTerms(terms: List<Term>) {
            var head = true
            for (term in terms){
                val currentCoef = coefs?.get(coefIndex) ?: 1L

                if (currentCoef != 0L) {
                    if (!head) {
                        node.append(" + ")
                    }
                    else {
                        head = false
                    }

                    if (currentCoef != 1L) {
                        node.append(currentCoef.toString())
                    }
                    node.append(term.toDisplayString())
                }
                coefIndex++
            }
        }

        appendTerms(leftSide)
        node.append(" -> ")
        appendTerms(rightSide)

        return node.toString()
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