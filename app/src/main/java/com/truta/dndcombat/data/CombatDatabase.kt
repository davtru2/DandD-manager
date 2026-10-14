package com.truta.dndcombat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Combatant::class, EnemyTemplate::class], version = 1, exportSchema = false)
abstract class CombatDatabase : RoomDatabase() {

    abstract fun combatDao(): CombatDao

    companion object {
        @Volatile private var INSTANCE: CombatDatabase? = null

        fun getInstance(context: Context): CombatDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CombatDatabase::class.java,
                    "combat_tracker.db"
                ).build().also { INSTANCE = it }
            }
    }
}
