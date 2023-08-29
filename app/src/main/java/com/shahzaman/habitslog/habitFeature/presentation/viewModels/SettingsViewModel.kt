package com.shahzaman.habitslog.habitFeature.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shahzaman.habitslog.habitFeature.presentation.Preferences

class SettingsViewModel : ViewModel() {
    var themeMode by mutableStateOf(
        Preferences.instance.getString(Preferences.themeKey, "system")
    )
}
