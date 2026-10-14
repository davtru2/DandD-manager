package com.truta.dndcombat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CrimsonRed = Color(0xFFB3261E)
val Parchment = Color(0xFFF3E9DC)
val Ink = Color(0xFF1B1B1F)
val Surface = Color(0xFF232326)
val Steel = Color(0xFF4A4E69)

private val DarkColors = darkColorScheme(
    primary = CrimsonRed,
    secondary = Steel,
    background = Ink,
    surface = Surface,
    onPrimary = Parchment,
    onSecondary = Parchment,
    onBackground = Parchment,
    onSurface = Parchment
)

@Composable
fun DndCombatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}
