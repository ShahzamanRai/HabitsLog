package com.shahzaman.habitslog.habitFeature.presentation


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahzaman.habitslog.habitFeature.data.database.CheckedItem
import com.shahzaman.habitslog.habitFeature.data.database.HabitDao
import com.shahzaman.habitslog.habitFeature.domain.habit.SortType
import com.shahzaman.habitslog.habitFeature.domain.mapper.Habit
import com.shahzaman.habitslog.habitFeature.domain.mapper.HabitMapper
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _sortType = MutableStateFlow(SortType.TIME)
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

    private val _selectedWeekDays = mutableStateListOf<Boolean>().apply {
        repeat(7) { add(false) }
    }
    val selectedWeekDays: List<Boolean> get() = _selectedWeekDays

    fun toggleWeekDay(dayIndex: Int) {
        _selectedWeekDays[dayIndex] = !_selectedWeekDays[dayIndex]
    }


    fun onEvent(event: HabitEvent) {
        when (event) {
            HabitEvent.CheckHabit -> {
                val selectedDays = selectedWeekDays.mapIndexedNotNull { index, isChecked ->
                    if (isChecked) index else null
                }
                val currentDate = LocalDate.now().toString()

                // Create a CheckedItem based on the selected weekdays and date
                val checkedItem = CheckedItem(
                    state = true,
                    date = currentDate
                )

                val habit = Habit(
                    title = state.value.title,
                    description = state.value.title,
                    isChecked = checkedItem,
                    time = state.value.time,
                    date = state.value.date
                )

                viewModelScope.launch {
                    dao.upsertHabit(HabitMapper.toEntity(habit))
                }
                _state.update {
                    it.copy(
                        isAddingHabit = false,
                        title = "",
                        time = "",
                        // Clear selected weekdays after checking habit
                        isChecked = checkedItem // Assign the CheckedItem
                    )
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
                val isChecked = state.value.isChecked
                val date = state.value.date
                val time = state.value.time

                if (title.isBlank() || time.isBlank()) {
                    return
                }

                val habit = Habit(
                    title = title,
                    description = title,
                    isChecked = isChecked,
                    date = date,
                    time = time
                )
                viewModelScope.launch {
                    dao.upsertHabit(HabitMapper.toEntity(habit))
                }
                _state.update {
                    it.copy(
                        isAddingHabit = false,
                        title = "",
                        time = ""
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
}