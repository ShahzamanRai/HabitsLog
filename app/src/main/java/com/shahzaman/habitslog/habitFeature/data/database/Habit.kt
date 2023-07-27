package com.shahzaman.habitslog.habitFeature.data.database

data class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val isChecked: Boolean
)