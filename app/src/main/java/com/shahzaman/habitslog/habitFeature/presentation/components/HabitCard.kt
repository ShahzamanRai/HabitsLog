package com.shahzaman.habitslog.habitFeature.presentation.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahzaman.habitslog.R
import com.shahzaman.habitslog.habitFeature.data.database.HabitEntity
import com.shahzaman.habitslog.habitFeature.presentation.navigation.NavRoutes
import com.shahzaman.habitslog.habitFeature.presentation.ui.theme.Patua_One

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCard(
    habitName: String,
    habitFrequency: String,
    onCheck: () -> Unit,
    onUnCheck: () -> Unit,
    habitEntity: HabitEntity,
    context: Context,
    navController: NavController
) {
    var checkedState by remember { mutableStateOf(habitEntity.isChecked) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = {
            navController.navigate(NavRoutes.Stat.route)
        },
        shape = MaterialTheme.shapes.large
    ) {

        LaunchedEffect(checkedState) {
            if (checkedState) {
                onCheck()
            } else {
                onUnCheck()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 24.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CustomCheckbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    if (checkedState) playSound(context)
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = habitName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = Patua_One
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = habitFrequency,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )
            }
        }
    }
}

fun playSound(
    context: Context
) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.done)
    mediaPlayer.setOnCompletionListener {
        mediaPlayer.release()
    }
    mediaPlayer.start()
}
