package com.shahzaman.habitslog.habitFeature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shahzaman.habitslog.habitFeature.data.database.HabitDatabase
import com.shahzaman.habitslog.habitFeature.presentation.components.AddHabitSheet
import com.shahzaman.habitslog.habitFeature.presentation.components.Header
import com.shahzaman.habitslog.habitFeature.presentation.components.MyNavigationBar
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavRoutes
import com.shahzaman.habitslog.habitFeature.presentation.navigation.SetupNavGraph
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.HabitsLogTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: HabitDatabase

    private val viewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController

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
                navController = rememberNavController()
                val state by viewModel.state.collectAsState()
                var selectedItem by rememberSaveable { mutableStateOf(0) }
                LaunchedEffect(selectedItem) {
                    val selectedScreen = when (selectedItem) {
                        0 -> NavRoutes.Home.route
                        1 -> NavRoutes.Stat.route
                        2 -> NavRoutes.Setting.route
                        else -> NavRoutes.Home.route
                    }
                    navController.navigate(selectedScreen) {
                        navController.popBackStack()
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Header(
                                onEvent = viewModel::onEvent
                            )
                        },
                        bottomBar = {
                            MyNavigationBar(
                                selectedItem = selectedItem
                            ) { index ->
                                selectedItem = index
                            }
                        }
                    ) { paddingValues ->
                        SetupNavGraph(
                            navHostController = navController, paddingValues = paddingValues,
                            state = state,
                            onEvent = viewModel::onEvent,
                            context = baseContext,
                            viewModel = settingsModel
                        )
                        if (state.isAddingHabit) {
                            AddHabitSheet(
                                state = state, onEvent = viewModel::onEvent
                            )
                        }
                    }

                }
            }
        }
    }
}
