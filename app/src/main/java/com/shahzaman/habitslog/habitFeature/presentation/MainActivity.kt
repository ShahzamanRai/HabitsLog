package com.shahzaman.habitslog.habitFeature.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.shahzaman.habitslog.habitFeature.data.database.HabitDatabase
import com.shahzaman.habitslog.habitFeature.data.worker.ResetHabitsWorker
import com.shahzaman.habitslog.habitFeature.presentation.components.AddHabitSheet
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavContainer
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavRoutes
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.HabitsLogTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: HabitDatabase

    private val viewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            resetCheckedState()
            val settingsModel: SettingsViewModel = viewModel()
            HabitsLogTheme(
                darkTheme = when (settingsModel.themeMode) {
                    "system" -> isSystemInDarkTheme()
                    else -> settingsModel.themeMode == "dark"
                }
            ) {
                installSplashScreen()
                val state by viewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavContainer(
                        settingsModel,
                        NavRoutes.Home,
                        state = state,
                        viewModel = viewModel,
                        baseContext = baseContext,
                        onEvent = viewModel::onEvent
                    )
                    if (state.isAddingHabit) {
                        AddHabitSheet(state = state, onEvent = viewModel::onEvent)
                    }
                }
            }
        }
    }

    private fun resetCheckedState() {
        // Calculate midnight time
        val midnight =
            LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli()
        Log.i(TAG, "resetCheckedState: $midnight")
// Schedule a job using WorkManager
        val constraints = Constraints.Builder()
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .build()

        val resetRequest = OneTimeWorkRequestBuilder<ResetHabitsWorker>()
            .setInitialDelay(midnight - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(baseContext).enqueue(resetRequest)

    }
}
