package com.shahzaman.habitslog.habitFeature.presentation

import com.shahzaman.habitslog.habitFeature.data.database.HabitEntity
import com.shahzaman.habitslog.habitFeature.domain.habit.SortType

sealed interface HabitEvent {
    object SaveHabit : HabitEvent
    data class SetTitle(val title: String) : HabitEvent
    data class SetTime(val time: String) : HabitEvent
    object ShowDialog : HabitEvent
    object HideDialog : HabitEvent
    object CheckHabit : HabitEvent
    data class SortHabit(val sortType: SortType) : HabitEvent
    data class DeleteHabit(val habit: HabitEntity) : HabitEvent
}