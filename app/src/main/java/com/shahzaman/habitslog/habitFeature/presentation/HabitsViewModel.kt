package com.shahzaman.habitslog.habitFeature.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahzaman.habitslog.habitFeature.data.database.HabitDao
import com.shahzaman.habitslog.habitFeature.domain.habit.SortType
import com.shahzaman.habitslog.habitFeature.domain.mapper.Habit
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
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
    private val dao: HabitDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.TITLE)
    private val _habits = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.TITLE -> dao.getHabitsByTitle()
                SortType.TIME -> dao.getHabitsByTime()
            }
        }
        .map { habitEntities ->
            habitEntities.map { HabitMapper.fromEntity(it) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HabitState())
    val state = combine(_state, _sortType, _habits) { state, sortType, habits ->
        state.copy(
            habits = habits,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HabitState())


    val totalHabitsFlow: Flow<Int> = dao.getTotalHabits()
    val checkedHabitsFlow: Flow<Int> = getCheckedHabitsCount()

    fun onEvent(event: HabitEvent) {
        when (event) {
            is HabitEvent.CheckHabit -> {
                val currentDate = LocalDate.now()

                viewModelScope.launch(Dispatchers.IO) {
                    val habit = dao.getHabitById(event.id)
                    val checkedDates = habit.checkedDates + currentDate
                    val updatedHabit =
                        habit.copy(isChecked = true, checkedDates = checkedDates)
                    dao.upsertHabit(updatedHabit)
                }

            }

            is HabitEvent.UnCheckHabit -> {
                LocalDate.now()

                viewModelScope.launch(Dispatchers.IO) {
                    val habit = dao.getHabitById(event.id)
                    val updatedCheckedDates = habit.checkedDates.filterNot { it == LocalDate.now() }
                    val updatedHabit =
                        habit.copy(isChecked = false, checkedDates = updatedCheckedDates)
                    dao.upsertHabit(updatedHabit)
                }

            }

            is HabitEvent.DeleteHabit -> {
                viewModelScope.launch {
                    dao.deleteHabit(event.habit)
                }
            }

            HabitEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingHabit = false
                    )
                }
            }

            HabitEvent.SaveHabit -> {
                val title = state.value.title
                val frequency = state.value.frequency
                val isChecked = state.value.isChecked
                val checkedDates = state.value.checkedHabits
                val time = state.value.time

                if (title.isBlank() || time.isBlank() || frequency.isBlank()) {
                    return
                }

                val habit = Habit(
                    title = title,
                    frequency = frequency,
                    isChecked = isChecked,
                    time = time,
                    checkedDates = checkedDates
                )
                viewModelScope.launch {
                    dao.upsertHabit(HabitMapper.toEntity(habit))
                }
                _state.update {
                    it.copy(
                        isAddingHabit = false,
                        title = "",
                        time = "",
                        frequency = ""
                    )
                }
            }

            is HabitEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is HabitEvent.SetFrequency -> {
                _state.update {
                    it.copy(
                        frequency = event.frequency
                    )
                }
            }

            is HabitEvent.SetTime -> {
                _state.update {
                    it.copy(
                        time = event.time
                    )
                }
            }

            HabitEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingHabit = true
                    )
                }
            }

            is HabitEvent.SortHabit -> {
                _sortType.value = event.sortType
            }

        }
    }

    private fun getCheckedHabitsCount(): Flow<Int> {
        return dao.getHabitsByTitle() // Use the appropriate query here
            .map { habits ->
                habits.count { it.isChecked }
            }
    }
}
