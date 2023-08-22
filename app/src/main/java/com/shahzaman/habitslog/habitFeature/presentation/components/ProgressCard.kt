package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.Patua_One

@Composable
fun ProgressCard(
    modifier: Modifier,
    totalHabits: Int,
    checkedHabits: Int,
) {
    val progress: Float = remember(checkedHabits, totalHabits) {
        val calculatedProgress = (checkedHabits.toFloat() / totalHabits.toFloat()) * 100f
        if (calculatedProgress.isNaN()) {
            0f
        } else {
            calculatedProgress
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You are almost there!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = Patua_One
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${checkedHabits}/${totalHabits} day goals completed",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            CircularProgressbar(
                progress = progress
            )
        }
    }
}
