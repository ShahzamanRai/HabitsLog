package com.shahzaman.habitslog.habitFeature.domain.mapper

import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import java.time.LocalDate

data class Habit(
    val id: Int = 0,
    val title: String,
    val frequency: String,
    val isChecked: CheckedItem = CheckedItem(false, date = listOf(LocalDate.now())),
    val time: String,
    val date: String,
)
