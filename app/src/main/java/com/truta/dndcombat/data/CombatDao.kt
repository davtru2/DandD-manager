package com.truta.dndcombat.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CombatDao {

    @Query("SELECT * FROM combatants ORDER BY initiative DESC, id ASC")
    fun getAllCombatants(): Flow<List<Combatant>>

    @Insert
    suspend fun insertCombatant(combatant: Combatant): Long

    @Update
    suspend fun updateCombatant(combatant: Combatant)

    @Delete
    suspend fun deleteCombatant(combatant: Combatant)

    @Query("DELETE FROM combatants")
    suspend fun clearCombat()

    @Query("SELECT * FROM enemy_templates ORDER BY name ASC")
    fun getAllTemplates(): Flow<List<EnemyTemplate>>

    @Insert
    suspend fun insertTemplate(template: EnemyTemplate): Long

    @Delete
    suspend fun deleteTemplate(template: EnemyTemplate)
}
