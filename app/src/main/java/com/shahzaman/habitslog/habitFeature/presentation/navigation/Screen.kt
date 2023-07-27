package com.shahzaman.habitslog.habitFeature.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Home : Screen(route = "home")
    object Stat : Screen(route = "stat")
    object Setting : Screen(route = "setting")
}
