package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitSheet(
    state: HabitState,
    onEvent: (HabitEvent) -> Unit
) {



    AlertDialog(
        onDismissRequest = { onEvent(HabitEvent.HideDialog) },
        confirmButton = {
            Button(onClick = {
                onEvent(HabitEvent.SaveHabit)
            }) {
                Text(text = "Save Habit")
            }
        },
        title = {
            Text(text = "Add Habit")
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(HabitEvent.SetTitle(it))
                    },
                    label = {
                        Text(text = "Title")
                    })
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.time,
                    onValueChange = {
                        onEvent(HabitEvent.SetTime(it))
                    },
                    label = {
                        Text(text = "Time")
                    },
                    placeholder = {
                        Text(
                            text = "Morning - Noon - Evening - Night",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.9f
                            )
                        )
                    })

            }
        }
    )

}