package com.shahzaman.habitslog.habitFeature.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem

class Converters {
    @TypeConverter
    fun fromCheckedItem(checkedItem: CheckedItem): String {
        return Gson().toJson(checkedItem)
    }

    @TypeConverter
    fun toCheckedItem(json: String): CheckedItem {
        return Gson().fromJson(json, CheckedItem::class.java)
    }
}
