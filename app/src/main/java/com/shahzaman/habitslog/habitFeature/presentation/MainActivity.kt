package com.shahzaman.habitslog.habitFeature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.shahzaman.habitslog.habitFeature.presentation.components.Header
import com.shahzaman.habitslog.habitFeature.presentation.components.MyNavigationBar
import com.shahzaman.habitslog.habitFeature.presentation.navigation.Screen
import com.shahzaman.habitslog.habitFeature.presentation.navigation.SetupNavGraph
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.HabitsLogTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var navController: NavHostController

        super.onCreate(savedInstanceState)
        setContent {
            HabitsLogTheme {
                navController = rememberNavController()
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
                    topBar = { Header() },
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
                    SetupNavGraph(navHostController = navController, paddingValues = paddingValues)
                }

            }
        }
    }

}
