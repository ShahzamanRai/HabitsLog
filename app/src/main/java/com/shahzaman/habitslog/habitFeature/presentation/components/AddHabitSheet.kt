package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
        val focusManager = LocalFocusManager.current

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
                TopAppBar(
                    title = {
                        Text(text = "Add Habit")
                    },
                    navigationIcon = {
                        CloseButton(onClick = { onEvent(HabitEvent.HideDialog) })
                    },
                    actions = {
                        SaveButton(onClick = { onEvent(HabitEvent.SaveHabit) })
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                HabitTextField(
                    value = state.title,
                    onValueChange = { onEvent(HabitEvent.SetTitle(it)) },
                    label = "Title",
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                HabitTextField(
                    value = state.frequency,
                    onValueChange = { onEvent(HabitEvent.SetFrequency(it)) },
                    label = "Frequency",
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                HabitTextField(
                    value = state.time,
                    onValueChange = { onEvent(HabitEvent.SetTime(it)) },
                    label = "Time",
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Exit) }
                )
            }
        }
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "Close Sheet")
    }
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = "Save")
    }
}

@Composable
private fun HabitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = keyboardActions
    )
}
