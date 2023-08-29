package com.shahzaman.habitslog.habitFeature.di

import androidx.lifecycle.ViewModel
import com.shahzaman.habitslog.habitFeature.presentation.viewModels.HabitViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HabitViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindHabitViewModel(
        viewModel: HabitViewModel
    ): ViewModel
}
