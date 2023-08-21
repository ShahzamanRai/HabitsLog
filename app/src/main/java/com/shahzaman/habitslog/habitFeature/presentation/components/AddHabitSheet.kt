package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitSheet(
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onEvent(HabitEvent.HideDialog) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopAppBar(title = {
                    Text(text = "Add Habit")
                },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(HabitEvent.HideDialog) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Sheet"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = { onEvent(HabitEvent.SaveHabit) }) {
                            Text(text = "Save")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(HabitEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(
                            text = "e.g. Exercise",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.9f
                            )
                        )
                    },
                    label = {
                        Text(text = "Title")
                    })
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.frequency,
                    onValueChange = {
                        onEvent(HabitEvent.SetFrequency(it))
                    },
                    label = {
                        Text(text = "Frequency")
                    },
                    placeholder = {
                        Text(
                            text = "e.g. Daily",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.9f
                            )
                        )
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
                            text = "e.g. Morning",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.9f
                            )
                        )
                    })
            }
        }
    }
}