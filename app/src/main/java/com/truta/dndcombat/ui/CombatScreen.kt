package com.truta.dndcombat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.truta.dndcombat.viewmodel.CombatViewModel

@Composable
fun CombatScreen(viewModel: CombatViewModel) {
    val combatants by viewModel.combatants.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { viewModel.nextRound() }, modifier = Modifier.weight(1f)) { Text("Ronda siguiente") }
            OutlinedButton(onClick = { viewModel.clearCombat() }, modifier = Modifier.weight(1f)) { Text("Reiniciar combate") }
        }
        Spacer(Modifier.height(12.dp))
        if (combatants.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Añade combatientes desde la pestaña Crear")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(combatants, key = { it.id }) { c ->
                    CombatantCard(combatant = c, viewModel = viewModel)
                }
            }
        }
    }
}
