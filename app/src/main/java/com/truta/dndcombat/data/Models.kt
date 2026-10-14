package com.truta.dndcombat.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Un ataque o habilidad de un enemigo (o de cualquier combatiente).
 * maxUses = null significa uso ilimitado.
 */
@Serializable
data class Attack(
    val name: String,
    val hitBonus: Int = 0,
    val damage: String = "",
    val maxUses: Int? = null,
    val usesLeft: Int? = null
)

/** Una condición/estado activo con las rondas que le quedan. */
@Serializable
data class ActiveCondition(
    val name: String,
    val roundsRemaining: Int
)

private val json = Json { ignoreUnknownKeys = true }

fun List<Attack>.toJson(): String = json.encodeToString(this)

fun String.toAttackList(): List<Attack> =
    if (this.isBlank()) emptyList() else try {
        json.decodeFromString(this)
    } catch (e: Exception) {
        emptyList()
    }

fun List<ActiveCondition>.toJsonConditions(): String = json.encodeToString(this)

fun String.toConditionList(): List<ActiveCondition> =
    if (this.isBlank()) emptyList() else try {
        json.decodeFromString(this)
    } catch (e: Exception) {
        emptyList()
    }

fun List<String>.toJsonStrings(): String = json.encodeToString(this)

fun String.toStringList(): List<String> =
    if (this.isBlank()) emptyList() else try {
        json.decodeFromString(this)
    } catch (e: Exception) {
        emptyList()
    }
