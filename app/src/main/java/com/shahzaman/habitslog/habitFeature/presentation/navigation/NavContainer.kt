package com.shahzaman.habitslog.habitFeature.presentation.navigation

import android.content.Context
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shahzaman.habitslog.R
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.viewModels.HabitViewModel
import com.shahzaman.habitslog.habitFeature.presentation.MainActivity
import com.shahzaman.habitslog.habitFeature.presentation.viewModels.SettingsViewModel
import com.shahzaman.habitslog.habitFeature.presentation.components.ClickableIcon
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.Patua_One

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavContainer(
    settingsViewModel: SettingsViewModel,
    initialTab: NavRoutes,
    state: HabitState,
    baseContext: Context,
    onEvent: (HabitEvent) -> Unit,
) {
    val viewModel: HabitViewModel = hiltViewModel()
    val context = LocalContext.current

    val navController = rememberNavController()
    val bottomNavItems = listOf(
        NavRoutes.Home,
        NavRoutes.Stat,
        NavRoutes.Setting,
    )

    var selectedRoute by remember {
        mutableStateOf(initialTab)
    }
    LaunchedEffect(Unit) {
        val activity = context as MainActivity
        activity.onBackPressedDispatcher.addCallback {
            navController.popBackStack()
        }
    }

    // listen for destination changes (e.g. back presses)
    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            bottomNavItems.firstOrNull { it.route == destination.route }
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
            NavRoutes.Setting ->
                Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)

            else -> Modifier
        },
        topBar = {
            when (selectedRoute) {
                NavRoutes.Setting -> SetupSettingTopBar(navController = navController)
                else -> SetupHomeStatTopBar(
                    selectedRoute = selectedRoute,
                    navController = navController,
                    onEvent = onEvent
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            SetupNavHost(
                navHostController = navController,
                state = state,
                onEvent = viewModel::onEvent,
                context = baseContext,
                settingsViewModel = settingsViewModel,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupSettingTopBar(navController: NavController) {
    // Logic to set up the top bar for the Setting route
    MediumTopAppBar(
        title = {
            Text(stringResource(R.string.setting))
        },
        navigationIcon = {
            ClickableIcon(imageVector = Icons.Default.ArrowBack) {
                navController.popBackStack()
            }
        },
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupHomeStatTopBar(
    selectedRoute: NavRoutes,
    navController: NavController,
    onEvent: (HabitEvent) -> Unit
) {
    // Logic to set up the top bar for the Home and Stat routes
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = selectedRoute.stringRes),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Patua_One
                )
            )
        },
        actions = {
            if (selectedRoute == NavRoutes.Home) {
                IconButton(onClick = { onEvent(HabitEvent.ShowDialog) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add habit",
                    )
                }
                IconButton(onClick = { navController.navigate(NavRoutes.Setting.route) }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                    )
                }
            }
        },
        navigationIcon = {
            if (selectedRoute == NavRoutes.Stat) {
                ClickableIcon(imageVector = Icons.Default.ArrowBack) {
                    navController.popBackStack()
                }
            }
        }
    )
}
