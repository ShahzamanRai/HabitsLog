package com.shahzaman.habitslog.habitFeature.presentation.navigation

import android.content.Context
import androidx.activity.addCallback
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.HabitViewModel
import com.shahzaman.habitslog.habitFeature.presentation.MainActivity
import com.shahzaman.habitslog.habitFeature.presentation.SettingsViewModel
import com.shahzaman.habitslog.habitFeature.presentation.components.ClickableIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavContainer(
    settingsModel: SettingsViewModel,
    initialTab: NavRoutes,
    state: HabitState,
    viewModel: HabitViewModel,
    baseContext: Context
) {
    val context = LocalContext.current

    val navController = rememberNavController()
    val bottomNavItems = listOf(
        NavRoutes.Home,
        NavRoutes.Stat,
        NavRoutes.Setting,
    )
    val navRoutes = bottomNavItems + NavRoutes.AddHabit

    var selectedRoute by remember {
        mutableStateOf(initialTab)
    }
    LaunchedEffect(Unit) {
        val activity = context as MainActivity
        activity.onBackPressedDispatcher.addCallback {
            if (selectedRoute != NavRoutes.AddHabit) activity.finish()
            else navController.popBackStack()
        }
    }

    // listen for destination changes (e.g. back presses)
    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            navRoutes.firstOrNull { it.route == destination.route }
                ?.let { selectedRoute = it }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(
        modifier = when (selectedRoute) {
            NavRoutes.AddHabit ->
                Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)

            else -> Modifier
        },
        topBar = {
            Crossfade(selectedRoute, label = "") { navRoute ->
                when (navRoute) {
                    NavRoutes.AddHabit -> LargeTopAppBar(
                        title = {
                            Text(stringResource(selectedRoute.stringRes))
                        },
                        navigationIcon = {
                            ClickableIcon(imageVector = Icons.Default.ArrowBack) {
                                navController.popBackStack()
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )

                    else -> TopAppBar(
                        title = {
                            Text(stringResource(selectedRoute.stringRes))
                        },
                        actions = {
                            ClickableIcon(imageVector = Icons.Default.Add) {
                                navController.navigate(NavRoutes.AddHabit.route)
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(
                tonalElevation = 5.dp
            ) {
                bottomNavItems.forEach {
                    NavigationBarItem(
                        label = {
                            Text(stringResource(it.stringRes))
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add, "Add habit"
                            )
                        },
                        selected = it == selectedRoute,
                        onClick = {
                            selectedRoute = it
                            navController.navigate(it.route)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            SetupNavGraph(
                navHostController = navController, paddingValues = paddingValues,
                state = state,
                onEvent = viewModel::onEvent,
                context = baseContext,
                viewModel = settingsModel
            )
        }
    }
}
