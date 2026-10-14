package com.truta.dndcombat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.truta.dndcombat.data.CombatDatabase
import com.truta.dndcombat.ui.CampaignManagerScreen
import com.truta.dndcombat.ui.CombatScreen
import com.truta.dndcombat.ui.CreateScreen
import com.truta.dndcombat.ui.IntroScreen
import com.truta.dndcombat.ui.Screen
import com.truta.dndcombat.ui.theme.DndCombatTheme
import com.truta.dndcombat.viewmodel.CombatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = CombatDatabase.getInstance(applicationContext).combatDao()
        setContent {
            DndCombatTheme {
                val viewModel: CombatViewModel = viewModel(factory = CombatViewModel.Factory(dao))
                MainScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(viewModel: CombatViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.INTRO) }

    BackHandler(enabled = currentScreen != Screen.INTRO) { currentScreen = Screen.INTRO }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentScreen.title) },
                navigationIcon = {
                    if (currentScreen != Screen.INTRO) {
                        IconButton(onClick = { currentScreen = Screen.INTRO }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (currentScreen) {
                Screen.INTRO -> IntroScreen(onNavigate = { currentScreen = it })
                Screen.CAMPAIGN -> CampaignManagerScreen()
                Screen.CREATURE_CREATOR -> CreateScreen(viewModel)
                Screen.BATTLE -> CombatScreen(viewModel)
            }
        }
    }
}
