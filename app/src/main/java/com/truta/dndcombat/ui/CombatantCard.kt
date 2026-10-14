package com.truta.dndcombat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.truta.dndcombat.data.ActiveCondition
import com.truta.dndcombat.data.Combatant
import com.truta.dndcombat.data.toAttackList
import com.truta.dndcombat.data.toConditionList
import com.truta.dndcombat.data.toStringList
import com.truta.dndcombat.viewmodel.CombatViewModel

@Composable
fun CombatantCard(combatant: Combatant, viewModel: CombatViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showAddCondition by remember { mutableStateOf(false) }
    var conditionName by remember { mutableStateOf("") }
    var conditionRounds by remember { mutableStateOf("") }

    val conditions = combatant.conditionsJson.toConditionList()
    val attacks = combatant.attacksJson.toAttackList()
    val resistances = combatant.resistancesJson.toStringList()

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text(
                        (if (combatant.isPlayer) "\uD83D\uDEE1 " else "\u2694 ") + combatant.name,
                        style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
                    )
                    Text("Iniciativa ${combatant.initiative} · CA ${combatant.ac}", style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = { viewModel.duplicateCombatant(combatant) }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "Duplicar")
                }
                if (!combatant.isPlayer) {
                    IconButton(onClick = { viewModel.saveAsTemplate(combatant) }) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar como plantilla")
                    }
                }
                IconButton(onClick = { viewModel.deleteCombatant(combatant) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.adjustHp(combatant, -1) }) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar vida")
                }
                Text(
                    "${combatant.currentHp}/${combatant.maxHp}" + if (combatant.tempHp > 0) " (+${combatant.tempHp})" else "",
                    style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 12.dp)
                )
                IconButton(onClick = { viewModel.adjustHp(combatant, 1) }) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar vida")
                }
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { viewModel.adjustHp(combatant, -5) }) { Text("-5") }
                TextButton(onClick = { viewModel.adjustHp(combatant, 5) }) { Text("+5") }
            }

            if (combatant.isPlayer && combatant.currentHp == 0 && !combatant.isDead) {
                Spacer(Modifier.height(8.dp))
                Text("Tiradas de salvación de muerte", style = MaterialTheme.typography.bodyMedium)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Éxitos: ${combatant.deathSaveSuccesses}")
                    Button(onClick = { viewModel.rollDeathSave(combatant, true) }) { Text("Éxito") }
                    Text("Fallos: ${combatant.deathSaveFailures}")
                    Button(onClick = { viewModel.rollDeathSave(combatant, false) }) { Text("Fallo") }
                }
            }
            if (combatant.isDead) {
                Text("\uD83D\uDC80 Ha muerto", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(8.dp))

            if (conditions.isNotEmpty()) {
                ConditionChips(conditions) { viewModel.removeCondition(combatant, it) }
            }
            TextButton(onClick = { showAddCondition = !showAddCondition }) {
                Text(if (showAddCondition) "Cancelar" else "+ Añadir condición")
            }
            if (showAddCondition) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        conditionName, { conditionName = it }, label = { Text("Condición") }, modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        conditionRounds, { conditionRounds = it }, label = { Text("Rondas") }, modifier = Modifier.width(90.dp)
                    )
                    Button(onClick = {
                        if (conditionName.isNotBlank()) {
                            viewModel.addCondition(combatant, conditionName, conditionRounds.toIntOrNull() ?: 1)
                            conditionName = ""; conditionRounds = ""; showAddCondition = false
                        }
                    }) { Text("OK") }
                }
            }

            TextButton(onClick = { expanded = !expanded }) { Text(if (expanded) "Ver menos" else "Ver más") }

            if (expanded) {
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("CA: ")
                    IconButton(onClick = { viewModel.adjustAc(combatant, -1) }) {
                        Icon(Icons.Default.Remove, contentDescription = "Restar CA")
                    }
                    Text("${combatant.ac}")
                    IconButton(onClick = { viewModel.adjustAc(combatant, 1) }) {
                        Icon(Icons.Default.Add, contentDescription = "Sumar CA")
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = combatant.isConcentrating, onCheckedChange = { viewModel.toggleConcentration(combatant) })
                    Text("Concentrado en un hechizo")
                }
                if (resistances.isNotEmpty()) {
                    Text("Resistencias: ${resistances.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
                }
                if (attacks.isNotEmpty()) {
                    Text("Ataques/habilidades", style = MaterialTheme.typography.titleSmall)
                    attacks.forEach { atk ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "${atk.name} (+${atk.hitBonus}, ${atk.damage})" +
                                    (atk.maxUses?.let { " — ${atk.usesLeft}/$it usos" } ?: ""),
                                modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall
                            )
                            if (atk.maxUses != null) {
                                TextButton(
                                    onClick = { viewModel.useAttackCharge(combatant, atk.name) },
                                    enabled = (atk.usesLeft ?: 0) > 0
                                ) { Text("Usar") }
                            }
                        }
                    }
                }
                var notes by remember(combatant.id) { mutableStateOf(combatant.notes) }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it; viewModel.updateNotes(combatant, it) },
                    label = { Text("Notas") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ConditionChips(conditions: List<ActiveCondition>, onRemove: (String) -> Unit) {
    Column {
        conditions.forEach { cond ->
            AssistChip(
                onClick = { onRemove(cond.name) },
                label = { Text("${cond.name} (${cond.roundsRemaining})") },
                trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Quitar", modifier = Modifier.size(16.dp)) }
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}
