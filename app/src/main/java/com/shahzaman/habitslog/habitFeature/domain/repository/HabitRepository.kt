package com.shahzaman.habitslog.habitFeature.domain.repository

import com.shahzaman.habitslog.habitFeature.data.database.HabitEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitRepository {
    suspend fun upsertHabit(habit: HabitEntity)
    suspend fun deleteHabit(habit: HabitEntity)
    suspend fun getHabitsByTime(): Flow<List<HabitEntity>>
    fun getHabitsByTitle(): Flow<List<HabitEntity>>
    suspend fun getHabitById(id: Int): HabitEntity
    suspend fun getAllHabits(): Flow<List<HabitEntity>>
    fun getTotalHabits(): Flow<Int>
    suspend fun updateCheckedDates(id: Int, dates: List<LocalDate>)
}

