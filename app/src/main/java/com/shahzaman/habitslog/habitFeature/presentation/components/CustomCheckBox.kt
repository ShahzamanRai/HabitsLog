package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconButton(
        onClick = { onCheckedChange(!checked) },
    ) {
        Icon(
            imageVector = if (checked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
            contentDescription = "Custom Checkbox",
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(), // Set interactionSource to null
                indication = null
            ){
                onCheckedChange(!checked)
            }
        )
    }
}