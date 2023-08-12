package com.shahzaman.habitslog.habitFeature.domain.mapper

import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import java.time.LocalDate

data class Habit(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isChecked: CheckedItem = CheckedItem(false, date = LocalDate.now().toString()),
    val time: String,
    val date: String,
)
