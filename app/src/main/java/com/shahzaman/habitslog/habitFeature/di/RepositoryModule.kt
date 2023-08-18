package com.shahzaman.habitslog.habitFeature.di

import com.shahzaman.habitslog.habitFeature.data.repository.HabitRepositoryImpl
import com.shahzaman.habitslog.habitFeature.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        habitsRepositoryImpl: HabitRepositoryImpl
    ): HabitRepository
}