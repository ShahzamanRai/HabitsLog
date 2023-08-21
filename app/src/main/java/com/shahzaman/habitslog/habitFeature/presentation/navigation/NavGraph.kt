package com.shahzaman.habitslog.habitFeature.presentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.SettingsViewModel
import com.shahzaman.habitslog.habitFeature.presentation.components.AddHabitSheet
import com.shahzaman.habitslog.habitFeature.presentation.screens.HomeScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.SettingScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.StatScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    context: Context,
    viewModel: SettingsViewModel
) {
    NavHost(navController = navHostController, startDestination = NavRoutes.Home.route) {
        composable(route = NavRoutes.Home.route)
        {
            HomeScreen(
                paddingValues = paddingValues,
                state = state,
                onEvent = onEvent,
                context = context
            )
        }

        composable(
            route = NavRoutes.Stat.route
        ) {
            StatScreen(paddingValues = paddingValues)
        }

        composable(
            route = NavRoutes.Setting.route
        ) {
            SettingScreen(
                paddingValues = paddingValues,
                settingsModel = viewModel
            )
        }

        composable(route = NavRoutes.AddHabit.route)
        {
            AddHabitSheet(
                state = state,
                onEvent = onEvent,
            )
        }
    }
}