package com.shahzaman.habitslog.habitFeature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.shahzaman.habitslog.habitFeature.data.database.HabitDatabase
import com.shahzaman.habitslog.habitFeature.presentation.components.AddHabitSheet
import com.shahzaman.habitslog.habitFeature.presentation.components.Header
import com.shahzaman.habitslog.habitFeature.presentation.components.MyNavigationBar
import com.shahzaman.habitslog.habitFeature.presentation.navigation.Screen
import com.shahzaman.habitslog.habitFeature.presentation.navigation.SetupNavGraph
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.HabitsLogTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: HabitDatabase

    private val viewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController

        super.onCreate(savedInstanceState)
        setContent {
            HabitsLogTheme {
                installSplashScreen()
                navController = rememberNavController()
                val state by viewModel.state.collectAsState()
                var selectedItem by remember { mutableStateOf(0) }
                LaunchedEffect(selectedItem) {
                    val selectedScreen = when (selectedItem) {
                        0 -> Screen.Home.route
                        1 -> Screen.Stat.route
                        2 -> Screen.Setting.route
                        else -> Screen.Home.route
                    }
                    navController.navigate(selectedScreen) {
                        navController.popBackStack()
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Header(
                            onEvent = viewModel::onEvent
                        )
                    },
                    bottomBar = {
                        MyNavigationBar(
                            modifier = Modifier,
                            selectedItem = selectedItem,
                            onNavigationItemClicked = { index ->
                                selectedItem = index
                            }
                        )
                    }
                ) { paddingValues ->
                    SetupNavGraph(
                        navHostController = navController, paddingValues = paddingValues,
                        state = state,
                        onEvent = viewModel::onEvent,
                        context = baseContext
                    )
                    if (state.isAddingHabit) {
                        AddHabitSheet(state = state, onEvent = viewModel::onEvent)
                    }
                }

            }
        }
    }

}
