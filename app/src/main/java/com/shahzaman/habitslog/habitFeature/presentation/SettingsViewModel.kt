package com.shahzaman.habitslog.habitFeature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    var themeMode by mutableStateOf(
        Preferences.instance.getString(Preferences.themeKey, "system")
    )
}
