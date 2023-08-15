package com.shahzaman.habitslog.habitFeature.presentation.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.R
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.components.HabitCard
import com.shahzaman.habitslog.habitFeature.presentation.components.ProgressCard

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    context: Context,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        val totalHabits = if (state.habits.isNotEmpty()) state.habits.size else 1
        val checkedHabits = 6
        val progress = ((checkedHabits.toFloat() / totalHabits.toFloat()) * 100F)
        ProgressCard(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            totalHabits = totalHabits,
            checkedHabits = checkedHabits,
            progress = progress
        )
        Divider()
        LazyColumn(
            modifier = Modifier
        ) {
            items(state.habits) { habit ->
                HabitCard(
                    habitName = habit.title,
                    habitFrequency = "Daily",
                    habitEntity = HabitMapper.toEntity(habit),
                    onCheck = {
                        onEvent(HabitEvent.CheckHabit(habit.id))
                    },
                    onUnCheck = {
                        onEvent(HabitEvent.UnCheckHabit(habit.id))
                    },
                    context = context
                )
            }
        }
    }

}
