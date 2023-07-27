package com.shahzaman.habitslog.habitFeature.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.shahzaman.habitslog.R

@Composable
fun MyNavigationBar(
    modifier: Modifier,
    selectedItem: Int,
    onNavigationItemClicked: (Int) -> Unit
) {
    val items = listOf(
        NavigationBarItemData("Home", R.drawable.ic_house),
        NavigationBarItemData("Stats", R.drawable.bar_chart),
        NavigationBarItemData("Settings", R.drawable.baseline_settings_24)
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { onNavigationItemClicked(index) },
                modifier = modifier,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

data class NavigationBarItemData(val label: String, val icon: Int)
