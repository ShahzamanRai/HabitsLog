package com.shahzaman.habitslog.habitFeature.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahzaman.habitslog.habitFeature.presentation.screens.HomeScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.SettingScreen
import com.shahzaman.habitslog.habitFeature.presentation.screens.StatScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route)
        {
            HomeScreen(paddingValues = paddingValues)
        }

        composable(
            route = Screen.Stat.route
        ) {
            StatScreen(paddingValues = paddingValues)
        }

        composable(
            route = Screen.Setting.route
        ) {
            SettingScreen(paddingValues = paddingValues)
        }
    }
}