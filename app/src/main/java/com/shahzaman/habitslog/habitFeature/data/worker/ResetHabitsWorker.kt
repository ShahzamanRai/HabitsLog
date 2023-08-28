package com.shahzaman.habitslog.habitFeature.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shahzaman.habitslog.habitFeature.domain.repository.HabitRepository
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ResetHabitsWorker @AssistedInject constructor(
    private val context: Context,
    private val workerParams: WorkerParameters,
    private val repository: HabitRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            performResetHabits()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun performResetHabits() {
        withContext(Dispatchers.IO) {
            val habits = repository.getAllHabits() // Modify this to get all habits
            habits.map { habits ->
                habits.forEach { habit ->
                    habit.copy(isChecked = false)
                    repository.upsertHabit(habit)
                }
            }
        }
    }
}
