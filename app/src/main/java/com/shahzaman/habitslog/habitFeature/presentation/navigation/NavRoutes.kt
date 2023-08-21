package com.shahzaman.habitslog.habitFeature.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shahzaman.habitslog.R

sealed class NavRoutes(
    val route: String,
    @StringRes val stringRes: Int,
    @DrawableRes val icon: Int
) {
    object Home : NavRoutes(
        route = "home",
        stringRes = R.string.home,
        icon = R.drawable.ic_house
    )

    object Stat : NavRoutes(
        route = "stat",
        stringRes = R.string.stat,
        icon = R.drawable.bar_chart
    )

    object Setting : NavRoutes(
        route = "setting",
        stringRes = R.string.setting,
        icon = R.drawable.baseline_settings_24
    )

    object AddHabit :
        NavRoutes(route = "add_habit", , stringRes = R.string.addHabit, icon = R.drawable.ic_house)
}
