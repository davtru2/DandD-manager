package com.truta.dndcombat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.truta.dndcombat.data.ActiveCondition
import com.truta.dndcombat.data.Attack
import com.truta.dndcombat.data.Combatant
import com.truta.dndcombat.data.CombatDao
import com.truta.dndcombat.data.EnemyTemplate
import com.truta.dndcombat.data.toAttackList
import com.truta.dndcombat.data.toConditionList
import com.truta.dndcombat.data.toJson
import com.truta.dndcombat.data.toJsonConditions
import com.truta.dndcombat.data.toJsonStrings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CombatViewModel(private val dao: CombatDao) : ViewModel() {

    val combatants: StateFlow<List<Combatant>> = dao.getAllCombatants()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val templates: StateFlow<List<EnemyTemplate>> = dao.getAllTemplates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addPlayer(name: String, maxHp: Int, ac: Int, initiative: Int) {
        viewModelScope.launch {
            dao.insertCombatant(
                Combatant(name = name, isPlayer = true, maxHp = maxHp, currentHp = maxHp, ac = ac, initiative = initiative)
            )
        }
    }

    fun addEnemyFromTemplate(template: EnemyTemplate, initiative: Int, customName: String? = null) {
        viewModelScope.launch {
            dao.insertCombatant(
                Combatant(
                    name = customName ?: template.name,
                    isPlayer = false,
                    maxHp = template.maxHp,
                    currentHp = template.maxHp,
                    ac = template.ac,
                    initiative = initiative,
                    resistancesJson = template.resistancesJson,
                    attacksJson = template.attacksJson
                )
            )
        }
    }

    fun addCustomEnemy(
        name: String,
        maxHp: Int,
        ac: Int,
        initiative: Int,
        resistances: List<String>,
        attacks: List<Attack>
    ) {
        viewModelScope.launch {
            dao.insertCombatant(
                Combatant(
                    name = name,
                    isPlayer = false,
                    maxHp = maxHp,
                    currentHp = maxHp,
                    ac = ac,
                    initiative = initiative,
                    resistancesJson = resistances.toJsonStrings(),
                    attacksJson = attacks.toJson()
                )
            )
        }
    }

    fun adjustHp(combatant: Combatant, delta: Int) {
        viewModelScope.launch {
            var newTemp = combatant.tempHp
            var newHp = combatant.currentHp
            if (delta < 0) {
                var damage = -delta
                if (newTemp > 0) {
                    val absorbed = minOf(newTemp, damage)
                    newTemp -= absorbed
                    damage -= absorbed
                }
                newHp = (newHp - damage).coerceAtLeast(0)
            } else {
                newHp = (newHp + delta).coerceAtMost(combatant.maxHp)
            }
            dao.updateCombatant(combatant.copy(currentHp = newHp, tempHp = newTemp))
        }
    }

    fun setTempHp(combatant: Combatant, amount: Int) {
        viewModelScope.launch { dao.updateCombatant(combatant.copy(tempHp = amount)) }
    }

    fun adjustAc(combatant: Combatant, delta: Int) {
        viewModelScope.launch { dao.updateCombatant(combatant.copy(ac = combatant.ac + delta)) }
    }

    fun adjustInitiative(combatant: Combatant, newInitiative: Int) {
        viewModelScope.launch { dao.updateCombatant(combatant.copy(initiative = newInitiative)) }
    }

    fun addCondition(combatant: Combatant, name: String, rounds: Int) {
        viewModelScope.launch {
            val current = combatant.conditionsJson.toConditionList().toMutableList()
            current.removeAll { it.name.equals(name, ignoreCase = true) }
            current.add(ActiveCondition(name, rounds))
            dao.updateCombatant(combatant.copy(conditionsJson = current.toJsonConditions()))
        }
    }

    fun removeCondition(combatant: Combatant, name: String) {
        viewModelScope.launch {
            val current = combatant.conditionsJson.toConditionList().filterNot { it.name == name }
            dao.updateCombatant(combatant.copy(conditionsJson = current.toJsonConditions()))
        }
    }

    /** Avanza una ronda: reduce en 1 las rondas restantes de cada condición y elimina las que llegan a 0. */
    fun nextRound() {
        viewModelScope.launch {
            combatants.value.forEach { c ->
                val current = c.conditionsJson.toConditionList()
                if (current.isNotEmpty()) {
                    val updated = current
                        .map { it.copy(roundsRemaining = it.roundsRemaining - 1) }
                        .filter { it.roundsRemaining > 0 }
                    dao.updateCombatant(c.copy(conditionsJson = updated.toJsonConditions()))
                }
            }
        }
    }

    fun toggleConcentration(combatant: Combatant) {
        viewModelScope.launch { dao.updateCombatant(combatant.copy(isConcentrating = !combatant.isConcentrating)) }
    }

    fun updateNotes(combatant: Combatant, notes: String) {
        viewModelScope.launch { dao.updateCombatant(combatant.copy(notes = notes)) }
    }

    fun useAttackCharge(combatant: Combatant, attackName: String) {
        viewModelScope.launch {
            val attacks = combatant.attacksJson.toAttackList().map {
                if (it.name == attackName && it.usesLeft != null && it.usesLeft > 0) {
                    it.copy(usesLeft = it.usesLeft - 1)
                } else it
            }
            dao.updateCombatant(combatant.copy(attacksJson = attacks.toJson()))
        }
    }

    fun rollDeathSave(combatant: Combatant, success: Boolean) {
        viewModelScope.launch {
            var successes = combatant.deathSaveSuccesses
            var failures = combatant.deathSaveFailures
            if (success) successes++ else failures++
            val isDead = failures >= 3
            val stabilized = successes >= 3
            dao.updateCombatant(
                combatant.copy(
                    deathSaveSuccesses = if (stabilized) 0 else successes,
                    deathSaveFailures = if (isDead || stabilized) 0 else failures,
                    isDead = isDead
                )
            )
        }
    }

    fun duplicateCombatant(combatant: Combatant) {
        viewModelScope.launch {
            val baseName = combatant.name.replace(Regex("\\s*\\(\\d+\\)$"), "")
            val existing = combatants.value.count { it.name.startsWith(baseName) }
            dao.insertCombatant(
                combatant.copy(
                    id = 0,
                    name = "$baseName (${existing + 1})",
                    currentHp = combatant.maxHp,
                    tempHp = 0,
                    conditionsJson = "[]",
                    deathSaveSuccesses = 0,
                    deathSaveFailures = 0,
                    isDead = false
                )
            )
        }
    }

    fun deleteCombatant(combatant: Combatant) {
        viewModelScope.launch { dao.deleteCombatant(combatant) }
    }

    fun clearCombat() {
        viewModelScope.launch { dao.clearCombat() }
    }

    fun saveAsTemplate(combatant: Combatant) {
        viewModelScope.launch {
            dao.insertTemplate(
                EnemyTemplate(
                    name = combatant.name.replace(Regex("\\s*\\(\\d+\\)$"), ""),
                    maxHp = combatant.maxHp,
                    ac = combatant.ac,
                    resistancesJson = combatant.resistancesJson,
                    attacksJson = combatant.attacksJson
                )
            )
        }
    }

    fun deleteTemplate(template: EnemyTemplate) {
        viewModelScope.launch { dao.deleteTemplate(template) }
    }

    class Factory(private val dao: CombatDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CombatViewModel(dao) as T
        }
    }
}
