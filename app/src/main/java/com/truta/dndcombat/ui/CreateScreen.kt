package com.truta.dndcombat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.truta.dndcombat.data.Attack
import com.truta.dndcombat.data.Combatant
import com.truta.dndcombat.data.EnemyTemplate
import com.truta.dndcombat.data.toJson
import com.truta.dndcombat.data.toJsonStrings
import com.truta.dndcombat.viewmodel.CombatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(viewModel: CombatViewModel) {
    val templates by viewModel.templates.collectAsState()
    var section by remember { mutableStateOf(0) } // 0 = jugador, 1 = enemigo, 2 = plantillas

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        SingleChoiceSegmentedButtonRow {
            listOf("Jugador", "Enemigo", "Plantillas").forEachIndexed { index, label ->
                SegmentedButton(
                    selected = section == index,
                    onClick = { section = index },
                    shape = SegmentedButtonDefaults.itemShape(index, 3)
                ) { Text(label) }
            }
        }
        Spacer(Modifier.height(16.dp))
        when (section) {
            0 -> AddPlayerForm(viewModel)
            1 -> AddEnemyForm(viewModel, templates)
            2 -> TemplatesList(viewModel, templates)
        }
    }
}

@Composable
private fun AddPlayerForm(viewModel: CombatViewModel) {
    var name by remember { mutableStateOf("") }
    var hp by remember { mutableStateOf("") }
    var ac by remember { mutableStateOf("") }
    var initiative by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(name, { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            hp, { hp = it }, label = { Text("Vida máxima") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            ac, { ac = it }, label = { Text("Armadura (CA)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            initiative, { initiative = it }, label = { Text("Iniciativa") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val hpInt = hp.toIntOrNull() ?: return@Button
                val acInt = ac.toIntOrNull() ?: return@Button
                if (name.isNotBlank()) {
                    viewModel.addPlayer(name, hpInt, acInt, initiative.toIntOrNull() ?: 0)
                    name = ""; hp = ""; ac = ""; initiative = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Añadir jugador") }
    }
}

@Composable
private fun AddEnemyForm(viewModel: CombatViewModel, templates: List<EnemyTemplate>) {
    var useTemplate by remember { mutableStateOf(templates.isNotEmpty()) }

    if (templates.isNotEmpty()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Desde plantilla", modifier = Modifier.weight(1f))
            Switch(checked = useTemplate, onCheckedChange = { useTemplate = it })
        }
        Spacer(Modifier.height(8.dp))
    }

    if (useTemplate && templates.isNotEmpty()) {
        AddFromTemplate(viewModel, templates)
    } else {
        AddCustomEnemy(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddFromTemplate(viewModel: CombatViewModel, templates: List<EnemyTemplate>) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(templates.first()) }
    var initiative by remember { mutableStateOf("") }
    var customName by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Plantilla") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            templates.forEach { t ->
                DropdownMenuItem(text = { Text(t.name) }, onClick = { selected = t; expanded = false })
            }
        }
    }
    Spacer(Modifier.height(8.dp))
    OutlinedTextField(
        customName, { customName = it },
        label = { Text("Nombre (opcional, para diferenciar copias)") }, modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        initiative, { initiative = it }, label = { Text("Iniciativa") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(8.dp))
    Button(
        onClick = {
            viewModel.addEnemyFromTemplate(selected, initiative.toIntOrNull() ?: 0, customName.ifBlank { null })
            customName = ""; initiative = ""
        },
        modifier = Modifier.fillMaxWidth()
    ) { Text("Añadir enemigo al combate") }
}

@Composable
private fun AddCustomEnemy(viewModel: CombatViewModel) {
    var name by remember { mutableStateOf("") }
    var hp by remember { mutableStateOf("") }
    var ac by remember { mutableStateOf("") }
    var initiative by remember { mutableStateOf("") }
    var resistancesText by remember { mutableStateOf("") }
    var attacks by remember { mutableStateOf(listOf<Attack>()) }
    var atkName by remember { mutableStateOf("") }
    var atkHit by remember { mutableStateOf("") }
    var atkDamage by remember { mutableStateOf("") }
    var atkUses by remember { mutableStateOf("") }
    var saveAsTemplate by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(name, { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            hp, { hp = it }, label = { Text("Vida máxima") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            ac, { ac = it }, label = { Text("Armadura (CA)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            initiative, { initiative = it }, label = { Text("Iniciativa") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            resistancesText, { resistancesText = it }, label = { Text("Resistencias (separadas por coma)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Ataques/habilidades", style = MaterialTheme.typography.titleSmall)
        attacks.forEach { atk ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "${atk.name} (+${atk.hitBonus}, ${atk.damage}${atk.maxUses?.let { ", x$it" } ?: ""})",
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { attacks = attacks - atk }) {
                    Icon(Icons.Default.Close, contentDescription = "Quitar ataque")
                }
            }
        }
        OutlinedTextField(atkName, { atkName = it }, label = { Text("Nombre ataque") }, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                atkHit, { atkHit = it }, label = { Text("Bono al golpe") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                atkDamage, { atkDamage = it }, label = { Text("Daño (ej. 2d6+3)") }, modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                atkUses, { atkUses = it }, label = { Text("Usos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                if (atkName.isNotBlank()) {
                    val uses = atkUses.toIntOrNull()
                    attacks = attacks + Attack(atkName, atkHit.toIntOrNull() ?: 0, atkDamage, uses, uses)
                    atkName = ""; atkHit = ""; atkDamage = ""; atkUses = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Añadir ataque a la lista") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = saveAsTemplate, onCheckedChange = { saveAsTemplate = it })
            Text("Guardar como plantilla reutilizable")
        }

        Button(
            onClick = {
                val hpInt = hp.toIntOrNull() ?: return@Button
                val acInt = ac.toIntOrNull() ?: return@Button
                if (name.isBlank()) return@Button
                val resistances = resistancesText.split(",").map { it.trim() }.filter { it.isNotBlank() }
                viewModel.addCustomEnemy(name, hpInt, acInt, initiative.toIntOrNull() ?: 0, resistances, attacks)
                if (saveAsTemplate) {
                    viewModel.saveAsTemplate(
                        Combatant(
                            name = name, isPlayer = false, maxHp = hpInt, currentHp = hpInt, ac = acInt, initiative = 0,
                            resistancesJson = resistances.toJsonStrings(), attacksJson = attacks.toJson()
                        )
                    )
                }
                name = ""; hp = ""; ac = ""; initiative = ""; resistancesText = ""; attacks = emptyList()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Añadir enemigo al combate") }
    }
}

@Composable
private fun TemplatesList(viewModel: CombatViewModel, templates: List<EnemyTemplate>) {
    if (templates.isEmpty()) {
        Text("Todavía no hay plantillas guardadas.")
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(templates, key = { it.id }) { t ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(t.name, style = MaterialTheme.typography.titleMedium)
                        Text("PV ${t.maxHp} · CA ${t.ac}", style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = { viewModel.deleteTemplate(t) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar plantilla")
                    }
                }
            }
        }
    }
}
