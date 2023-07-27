package com.shahzaman.habitslog.habitFeature.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.habitFeature.presentation.components.HabitCard
import com.shahzaman.habitslog.habitFeature.presentation.components.ProgressCard

@Composable
fun HomeScreen(
    paddingValues: PaddingValues
) {
    val habits = arrayOf("Exercise", "Wake Up Early", "Early to Bed")
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        ProgressCard(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        )
        Divider()
        habits.forEach { name ->
            HabitCard(habitName = name, habitFrequency = "Daily")
        }
    }
}
