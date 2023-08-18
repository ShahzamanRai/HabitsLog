package com.shahzaman.habitslog.habitFeature.presentation

import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import com.shahzaman.habitslog.habitFeature.domain.habit.SortType
import com.shahzaman.habitslog.habitFeature.domain.mapper.Habit
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class HabitState(
    val habits: List<Habit> = emptyList(),
    val title: String = "",
    var isChecked: CheckedItem = CheckedItem(
        false,
        listOf(LocalDate.now())
    ),
    val isAddingHabit: Boolean = false,
    val sortType: SortType = SortType.TIME,
    val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM")),
    val time: String = ""
)