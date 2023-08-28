package com.shahzaman.habitslog.habitFeature.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "frequency") val frequency: String,
    @ColumnInfo(name = "isChecked") val isChecked: Boolean,
    @ColumnInfo(name = "checked_dates") val checkedDates: List<LocalDate>,
    @ColumnInfo(name = "time") val time: String,
)

