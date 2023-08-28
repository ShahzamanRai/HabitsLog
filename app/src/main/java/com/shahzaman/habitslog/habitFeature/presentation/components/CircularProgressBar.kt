package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressbar(
    progress: Float = 0f,
    size: Dp = 60.dp,
    thickness: Dp = 8.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    backgroundIndicatorColor: Color = foregroundIndicatorColor.copy(alpha = 0.5f),
    extraSizeForegroundIndicator: Dp = 4.dp
) {

    // It remembers the number value
    var numberR by remember {
        mutableFloatStateOf(progress)
    }

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = numberR,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(progress) {
        numberR = progress
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)

    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {

            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (animateNumber.value / 100) * 360

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    (thickness + extraSizeForegroundIndicator).toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        // Text that shows number inside the circle
        Text(
            text = (animateNumber.value).toInt().toString() + "%",
            style = MaterialTheme.typography.titleMedium
        )
    }
}