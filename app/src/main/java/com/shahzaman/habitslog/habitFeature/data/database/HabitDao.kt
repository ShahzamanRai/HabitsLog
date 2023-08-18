package com.shahzaman.habitslog.habitFeature.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY time ASC")
    fun getHabitsByTime(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits ORDER BY title ASC")
    fun getHabitsByTitle(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitById(id: Int): HabitEntity

    @Upsert
    suspend fun upsertHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

}
