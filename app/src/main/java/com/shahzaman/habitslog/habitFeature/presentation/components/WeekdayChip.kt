package com.shahzaman.habitslog.habitFeature.presentation.components

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shahzaman.habitslog.R
import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDayChip(
    state: HabitState
) {
    var mediaPlayer: MediaPlayer
    val weekDays = arrayOf("M", "T", "W", "T", "F", "S", "S")
    val selectedState =
        remember { mutableStateListOf<Boolean>().apply { repeat(weekDays.size) { add(false) } } }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
    ) {
        weekDays.forEachIndexed { index, alphabet ->
            val isChecked by rememberUpdatedState(selectedState[index])
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(percent = 50) // 50% for perfectly round shape
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        selectedState[index] = !selectedState[index]
                        state.isChecked = CheckedItem(
                            isChecked,
                            LocalDate
                                .now()
                                .toString()
                        )
                        mediaPlayer = MediaPlayer.create(context, R.raw.done)
                        mediaPlayer.setOnPreparedListener {
                            mediaPlayer.start()
                        }
                        mediaPlayer.setOnCompletionListener {
                            mediaPlayer.release()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Checked",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(
                        text = alphabet,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
