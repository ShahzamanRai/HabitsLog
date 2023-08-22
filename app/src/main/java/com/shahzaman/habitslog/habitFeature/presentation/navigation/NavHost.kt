package com.shahzaman.habitslog.habitFeature.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.HabitViewModel
import com.shahzaman.habitslog.habitFeature.presentation.SettingsViewModel
import com.shahzaman.habitslog.habitFeature.presentation.screens.HomeScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.SettingScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.StatScreen

@Composable
fun SetupNavHost(
    navHostController: NavHostController,
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    context: Context,
    settingsViewModel: SettingsViewModel,
    habitsViewModel: HabitViewModel
) {
    NavHost(navController = navHostController, startDestination = NavRoutes.Home.route) {
        composable(route = NavRoutes.Home.route)
        {
            HomeScreen(
                state = state,
                onEvent = onEvent,
                context = context,
                navController = navHostController,
                viewModel = habitsViewModel
            )
        }
        composable(
            route = NavRoutes.Stat.route,

            ) {
            StatScreen()
        }

        composable(
            route = NavRoutes.Setting.route
        ) {
            SettingScreen(
                settingsModel = settingsViewModel
            )
        }
    }
}