package com.shahzaman.habitslog.habitFeature.presentation

import com.shahzaman.habitslog.habitFeature.domain.habit.SortType
import com.shahzaman.habitslog.habitFeature.domain.mapper.Habit
import java.time.LocalDate

data class HabitState(
    val habits: List<Habit> = emptyList(),
    val title: String = "",
    val frequency: String = "",
    var isChecked: Boolean = false,
    var checkedHabits: List<LocalDate> = emptyList(),
    val isAddingHabit: Boolean = false,
    val sortType: SortType = SortType.TIME,
    val time: String = ""
)