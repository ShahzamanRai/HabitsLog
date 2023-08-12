package com.shahzaman.habitslog.habitFeature.di

import android.content.Context
import androidx.room.Room
import com.shahzaman.habitslog.habitFeature.data.database.HabitDao
import com.shahzaman.habitslog.habitFeature.data.database.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitDatabase::class.java,
            "habit_database"
        ).build()
    }

    @Provides
    fun provideHabitDao(habitDatabase: HabitDatabase): HabitDao {
        return habitDatabase.dao
    }
}