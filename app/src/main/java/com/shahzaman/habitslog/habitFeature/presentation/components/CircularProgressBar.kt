package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    // Animation
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    // Display the progress percentage
    val progressText = "${animatedProgress.value.toInt()}%"

    // Draw the CircularProgressbar
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {
        // Draw circles
        DrawCircularProgressbar(
            size = size,
            thickness = thickness,
            animatedProgress = animatedProgress.value,
            foregroundIndicatorColor = foregroundIndicatorColor,
            backgroundIndicatorColor = backgroundIndicatorColor,
            extraSizeForegroundIndicator = extraSizeForegroundIndicator
        )

        // Display progress percentage
        Text(
            text = progressText,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun DrawCircularProgressbar(
    size: Dp,
    thickness: Dp,
    animatedProgress: Float,
    foregroundIndicatorColor: Color,
    backgroundIndicatorColor: Color,
    extraSizeForegroundIndicator: Dp
) {
    Canvas(
        modifier = Modifier
            .size(size = size)
    ) {
        // Draw the background circle
        drawCircle(
            color = backgroundIndicatorColor,
            radius = size.toPx() / 2,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
        )

        // Calculate sweep angle for progress
        val sweepAngle = (animatedProgress / 100) * 360

        // Draw the foreground circle
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
}
