package com.shahzaman.habitslog.habitFeature.presentation.navigation

import androidx.annotation.StringRes
import com.shahzaman.habitslog.R

sealed class NavRoutes(
    val route: String,
    @StringRes val stringRes: Int,
) {
    object Home : NavRoutes(
        route = "home",
        stringRes = R.string.home
    )

    object Stat : NavRoutes(
        route = "stat",
        stringRes = R.string.stat
    )

    object Setting : NavRoutes(
        route = "setting",
        stringRes = R.string.setting
    )
}
