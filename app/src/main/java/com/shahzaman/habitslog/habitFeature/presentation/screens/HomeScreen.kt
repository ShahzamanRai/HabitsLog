package com.shahzaman.habitslog.habitFeature.presentation.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import com.shahzaman.habitslog.habitFeature.presentation.HabitViewModel
import com.shahzaman.habitslog.habitFeature.presentation.components.HabitCard
import com.shahzaman.habitslog.habitFeature.presentation.components.ProgressCard

@Composable
fun HomeScreen(
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    context: Context,
    navController: NavController,
    viewModel: HabitViewModel
) {
    Column(
        modifier = Modifier
    ) {

        var visible by remember {
            mutableStateOf(true)
        }
        val totalHabits by viewModel.totalHabitsFlow.collectAsState(initial = 0)
        val checkedHabits by viewModel.checkedHabitsFlow.collectAsState(initial = 0)

        ProgressCard(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            totalHabits = totalHabits,
            checkedHabits = checkedHabits,
        )
        Divider()
        LazyColumn(
        ) {
            items(state.habits) { habit ->
                AnimatedVisibility(visible = visible) {
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
                        context = context,
                        navController = navController
                    )
                }
            }
        }
    }

}
