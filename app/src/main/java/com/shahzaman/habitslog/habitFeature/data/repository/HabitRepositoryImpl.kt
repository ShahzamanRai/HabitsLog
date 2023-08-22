package com.shahzaman.habitslog.habitFeature.data.repository

import com.shahzaman.habitslog.habitFeature.data.database.HabitDao
import com.shahzaman.habitslog.habitFeature.data.database.HabitEntity
import com.shahzaman.habitslog.habitFeature.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val habitDao: HabitDao) : HabitRepository {


    override suspend fun upsertHabit(habit: HabitEntity) {
        habitDao.upsertHabit(habit)
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    override suspend fun getHabitsByTime(): Flow<List<HabitEntity>> {
        return habitDao.getHabitsByTime()
    }

    override suspend fun getHabitsByTitle(): Flow<List<HabitEntity>> {
        return habitDao.getHabitsByTitle()
    }

    override suspend fun getHabitById(id: Int): HabitEntity {
        return habitDao.getHabitById(id = id)
    }

    override suspend fun getTotalHabits(): Flow<Int> {
        return habitDao.getTotalHabits()
    }

    override suspend fun checkedHabits(): Flow<Int> {
        return checkedHabits()
    }
}
