package com.truta.dndcombat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Plantilla de enemigo guardada entre sesiones, para añadirlo rápido a un combate futuro. */
@Entity(tableName = "enemy_templates")
data class EnemyTemplate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val maxHp: Int,
    val ac: Int,
    val resistancesJson: String = "[]",
    val attacksJson: String = "[]"
)
