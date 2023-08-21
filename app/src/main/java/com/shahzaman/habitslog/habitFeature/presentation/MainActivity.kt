package com.shahzaman.habitslog.habitFeature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shahzaman.habitslog.habitFeature.data.database.HabitDatabase
import com.shahzaman.habitslog.habitFeature.presentation.components.AddHabitSheet
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavContainer
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavRoutes
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.HabitsLogTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: HabitDatabase

    private val viewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsModel: SettingsViewModel = viewModel()
            HabitsLogTheme(
                darkTheme = when (settingsModel.themeMode) {
                    "system" -> isSystemInDarkTheme()
                    else -> settingsModel.themeMode == "dark"
                }
            ) {
                installSplashScreen()
                val state by viewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavContainer(
                        settingsModel,
                        NavRoutes.Home,
                        state = state,
                        viewModel = viewModel,
                        baseContext = baseContext,
                        onEvent = viewModel::onEvent
                    )
                    if (state.isAddingHabit) {
                        AddHabitSheet(state = state, onEvent = viewModel::onEvent)
                    }
                }
            }
        }
    }
}
