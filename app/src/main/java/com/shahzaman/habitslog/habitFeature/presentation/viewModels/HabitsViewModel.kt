package com.shahzaman.habitslog.habitFeature.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahzaman.habitslog.habitFeature.domain.habit.SortType
import com.shahzaman.habitslog.habitFeature.domain.mapper.Habit
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
import com.shahzaman.habitslog.habitFeature.domain.repository.HabitRepository
import com.shahzaman.habitslog.habitFeature.presentation.HabitEvent
import com.shahzaman.habitslog.habitFeature.presentation.HabitState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.TITLE)
    private val _habits = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.TITLE -> habitRepository.getHabitsByTitle()
            SortType.TIME -> habitRepository.getHabitsByTime()
        }
    }.map { habitEntities ->
        habitEntities.map { HabitMapper.fromEntity(it) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HabitState())
    val state = combine(_state, _sortType, _habits) { state, sortType, habits ->
        state.copy(
            habits = habits, sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HabitState())


    val totalHabitsFlow: Flow<Int> = habitRepository.getTotalHabits()
    val checkedHabitsFlow: Flow<Int> = getCheckedHabitsCount()

    fun onEvent(event: HabitEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is HabitEvent.CheckHabit -> {
                    val currentDate = LocalDate.now()
                    val habit = habitRepository.getHabitById(event.id)
                    val checkedDates = habit.checkedDates + currentDate
                    val updatedHabit = habit.copy(isChecked = true, checkedDates = checkedDates)
                    habitRepository.upsertHabit(updatedHabit)
                }

                is HabitEvent.UnCheckHabit -> {
                    val habit = habitRepository.getHabitById(event.id)
                    val updatedCheckedDates =
                        habit.checkedDates.filterNot { it == LocalDate.now() }
                    val updatedHabit =
                        habit.copy(isChecked = false, checkedDates = updatedCheckedDates)
                    habitRepository.upsertHabit(updatedHabit)

                }

                is HabitEvent.DeleteHabit -> {
                    habitRepository.deleteHabit(event.habit)
                }

                HabitEvent.HideDialog -> {
                    updateState { it.copy(isAddingHabit = false) }
                }

                HabitEvent.SaveHabit -> {
                    val title = state.value.title
                    val frequency = state.value.frequency
                    val isChecked = state.value.isChecked
                    val checkedDates = state.value.checkedHabits
                    val time = state.value.time

                    if (title.isBlank() || time.isBlank() || frequency.isBlank()) {
                        return@launch
                    }

                    val habit = Habit(
                        title = title,
                        frequency = frequency,
                        isChecked = isChecked,
                        time = time,
                        checkedDates = checkedDates
                    )
                    habitRepository.upsertHabit(HabitMapper.toEntity(habit))
                    _state.update {
                        it.copy(
                            isAddingHabit = false, title = "", time = "", frequency = ""
                        )
                    }
                }

                is HabitEvent.SetTitle -> {
                    updateState { it.copy(title = event.title) }
                }

                is HabitEvent.SetFrequency -> {
                    updateState { it.copy(frequency = event.frequency) }
                }

                is HabitEvent.SetTime -> {
                    updateState { it.copy(time = event.time) }
                }

                HabitEvent.ShowDialog -> {
                    updateState { it.copy(isAddingHabit = true) }
                }

                is HabitEvent.SortHabit -> {
                    _sortType.value = event.sortType
                }

            }
        }

    }

    private fun getCheckedHabitsCount(): Flow<Int> {
        return habitRepository.getHabitsByTitle().map { habits ->
            habits.count { it.isChecked }
        }
    }

    private fun updateState(update: (HabitState) -> HabitState) {
        _state.value = update(_state.value)
    }
}
