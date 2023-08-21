package com.shahzaman.habitslog.habitFeature.domain.mapper

import com.shahzaman.habitslog.habitFeature.data.database.HabitEntity

object HabitMapper {
    fun fromEntity(entity: HabitEntity): Habit {
        return Habit(
            id = entity.id,
            title = entity.title,
            frequency = entity.frequency,
            isChecked = entity.isChecked,
            time = entity.time,
            date = entity.date
        )
    }

    fun toEntity(habit: Habit): HabitEntity {
        return HabitEntity(
            id = habit.id,
            title = habit.title,
            frequency = habit.frequency,
            isChecked = habit.isChecked,
            time = habit.time,
            date = habit.date
        )
    }
}
