package com.shahzaman.habitslog.habitFeature.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.components.HabitCard
import com.shahzaman.habitslog.habitFeature.presentation.components.ProgressCard

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HabitState,
    onEvent: (HabitEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        ProgressCard(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        )
        Divider()
        LazyColumn {
            items(state.habits) { habit ->
                HabitCard(
                    habitName = habit.title, habitFrequency = "Daily",
                    onEvent = onEvent,
                    onclick = {
                        onEvent(HabitEvent.CheckHabit(habit.id))
                    }
                )
            }
        }
    }

}
