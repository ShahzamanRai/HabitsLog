package com.shahzaman.habitslog.habitFeature.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDateList(localDateList: List<LocalDate>): String {
        return localDateList.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toLocalDateList(data: String): List<LocalDate> {
        if (data.isEmpty()) {
            return emptyList()
        }
        return data.split(",").map { LocalDate.parse(it) }
    }

}
