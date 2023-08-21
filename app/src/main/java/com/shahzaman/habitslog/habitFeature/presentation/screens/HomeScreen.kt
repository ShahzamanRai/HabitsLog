package com.shahzaman.habitslog.habitFeature.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.components.HabitCard
import com.shahzaman.habitslog.habitFeature.presentation.components.ProgressCard
import kotlinx.coroutines.delay

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
        var totalHabits by remember {
            mutableStateOf(0)
        }
        LaunchedEffect(Unit) {
            delay(100)
            totalHabits = state.habits.size
        }
        val checkedHabits = 6
        val progress by remember {
            mutableStateOf(
                if (totalHabits != 0) {
                    ((checkedHabits.toFloat() / totalHabits.toFloat()) * 100F)
                } else {
                    0f
                }
            )
        }
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
        ) {
            items(state.habits) { habit ->
                HabitCard(
                    habitName = habit.title,
                    habitFrequency = habit.frequency,
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
