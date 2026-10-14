package com.truta.dndcombat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun IntroScreen(onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "\u2694 Rastreador de Combate D&D",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(40.dp))
        IntroOption(
            title = "Gestor de Campaña",
            subtitle = "Organiza tus campañas y sesiones",
            onClick = { onNavigate(Screen.CAMPAIGN) }
        )
        Spacer(Modifier.height(16.dp))
        IntroOption(
            title = "Creador de Criaturas",
            subtitle = "Crea jugadores y monstruos",
            onClick = { onNavigate(Screen.CREATURE_CREATOR) }
        )
        Spacer(Modifier.height(16.dp))
        IntroOption(
            title = "Gestor de Batalla",
            subtitle = "Lleva el combate en curso",
            onClick = { onNavigate(Screen.BATTLE) }
        )
    }
}

@Composable
private fun IntroOption(title: String, subtitle: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
