package com.shahzaman.habitslog.habitFeature.domain.mapper

import java.time.LocalDate

data class Habit(
    val id: Int = 0,
    val title: String,
    val frequency: String,
    val isChecked: Boolean,
    val checkedDates: List<LocalDate>,
    val time: String,
)
