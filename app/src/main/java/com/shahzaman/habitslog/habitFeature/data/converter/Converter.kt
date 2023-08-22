package com.shahzaman.habitslog.habitFeature.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromString(value: String): CheckedItem {
        val parts = value.split(";")
        val state = parts[0].toBoolean()
        val dateStrings = parts[1].split(",")
        val dates = dateStrings.map { LocalDate.parse(it, formatter) }
        return CheckedItem(state, dates)
    }

    @TypeConverter
    fun toString(checkedItem: CheckedItem): String {
        val stateString = checkedItem.state.toString()
        val dateStrings = checkedItem.date.joinToString(",") { it.format(formatter) }
        return "$stateString;$dateStrings"
    }
}
