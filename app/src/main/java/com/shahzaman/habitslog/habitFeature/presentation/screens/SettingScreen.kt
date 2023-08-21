package com.shahzaman.habitslog.habitFeature.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.R
import com.shahzaman.habitslog.habitFeature.presentation.Preferences
import com.shahzaman.habitslog.habitFeature.presentation.SettingsViewModel
import com.shahzaman.habitslog.habitFeature.presentation.components.ButtonGroupPref
import com.shahzaman.habitslog.habitFeature.presentation.components.SettingsCategory


@Composable
fun SettingScreen(
    settingsModel: SettingsViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingsCategory(stringResource(R.string.appearance))
            ButtonGroupPref(
                preferenceKey = Preferences.themeKey,
                title = stringResource(R.string.theme),
                options = listOf(R.string.system, R.string.light, R.string.dark).map {
                    stringResource(it)
                },
                values = listOf("system", "light", "dark"),
                defaultValue = "system"
            ) {
                settingsModel.themeMode = it
            }
        }
    }
}
