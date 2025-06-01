package com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.data.local.elements
import com.proyectoPdm.seashellinc.data.local.model.Element
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.ElementCard
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Preview
@Composable
fun PeriodicTableScreen(/*periodicTableViewModel: PeriodicTableViewModel*/) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    // TODO Esta parte se debe de poner en el viewModel
    //TODO Hasta aqui
    val elements: List<Element> = elements
    val maxPeriod = elements.maxOf { it.period }
    val maxGroup = 19

    val table = Array(maxPeriod + 1) { Array<Element?>(maxGroup + 1) { null } }
    elements.forEach { element ->
        table[element.period][element.group] = element
    }

    val tableByGroup = (1..maxGroup).map { group ->
        (1..maxPeriod).map { period ->
            table[period][group]
        }
    }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = navigationBarHeight),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MainBlue),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LogoComponent(modifier = Modifier.size(60.dp), 1f)
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth()){
            Text(
                "Tabla periódica de\nlos elementos",
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textAlign = TextAlign.End,
                color = CitrineBrown,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            Spacer(Modifier.height(20.dp))
            Row(Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(30.dp))
                AppGoBackButton(100.dp) { }
            }
            Spacer(Modifier.height(10.dp))
            Row {
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {

                    itemsIndexed(tableByGroup) { _, column ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            column.forEachIndexed { periodIndex, element ->
                                if (periodIndex == 7) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                if (element != null) {
                                    ElementCard(element = element, onClick = { })
                                } else {
                                    Spacer(modifier = Modifier.size(75.dp))
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}