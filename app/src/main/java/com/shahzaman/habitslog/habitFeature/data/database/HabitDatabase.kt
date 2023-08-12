package com.shahzaman.habitslog.habitFeature.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shahzaman.habitslog.habitFeature.data.converter.Converters

@Database(
    entities = [HabitEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract val dao: HabitDao
}
