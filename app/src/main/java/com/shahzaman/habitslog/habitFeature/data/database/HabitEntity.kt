package com.shahzaman.habitslog.habitFeature.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "isChecked") val isChecked: CheckedItem,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "date") val date: String
)

data class CheckedItem(
    @ColumnInfo(name = "state") val state: Boolean,
    @ColumnInfo(name = "date") val date: String
)
