package com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalEquationBalancer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Preview
@Composable
fun EquationBalancerScreen(
    // viewModel: EquationBalancerViewModel = HiltViewModel()
    viewModel: EquationBalancerViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = uiState.formulaInput,
            onValueChange = { viewModel.onFormulaInputChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )
        Button(
            onClick = { viewModel.onBalanceButtonClicked() },
            enabled = !uiState.isLoading
        ) {
            Text(if (uiState.isLoading) "Balanceando..." else "Balancear")
        }
        Spacer(Modifier.height(16.dp))

        uiState.errorMessage?.let { message ->
            Text(text = message, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(16.dp))

        uiState.balancedEquation?.let { (equation, coefficients) ->
            val annotatedEquation = equation.toAnnotatedString(coefficients)
            Text(
                text = annotatedEquation,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}