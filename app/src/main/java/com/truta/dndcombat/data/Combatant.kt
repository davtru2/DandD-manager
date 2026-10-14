package com.truta.dndcombat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Un combatiente (jugador o enemigo) dentro del combate activo.
 * Las listas (ataques, condiciones, resistencias) se guardan como JSON
 * para no complicar el esquema con tablas relacionadas.
 */
@Entity(tableName = "combatants")
data class Combatant(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val isPlayer: Boolean,
    val maxHp: Int,
    val currentHp: Int,
    val tempHp: Int = 0,
    val ac: Int,
    val initiative: Int,
    val conditionsJson: String = "[]",
    val resistancesJson: String = "[]",
    val attacksJson: String = "[]",
    val notes: String = "",
    val isConcentrating: Boolean = false,
    val deathSaveSuccesses: Int = 0,
    val deathSaveFailures: Int = 0,
    val isDead: Boolean = false
)
