package com.shahzaman.habitslog.habitFeature.presentation.navigation

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.viewModels.SettingsViewModel
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
) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(
            route = NavRoutes.Home.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(300, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(300, easing = LinearEasing),
                )
            }
        )
        {
            HomeScreen(
                state = state,
                onEvent = onEvent,
                context = context,
                navController = navHostController,
            )
        }
        composable(
            route = NavRoutes.Stat.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(300, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(300, easing = LinearEasing)
                )
            }
        ) {
            StatScreen()
        }

        composable(
            route = NavRoutes.Setting.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            }
        ) {
            SettingScreen(
                settingsModel = settingsViewModel
            )
        }
    }
}