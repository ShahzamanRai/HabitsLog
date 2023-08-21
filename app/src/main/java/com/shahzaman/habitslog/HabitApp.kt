package com.shahzaman.habitslog

import android.app.Application
import com.shahzaman.habitslog.habitFeature.presentation.Preferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HabitApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
    }
}