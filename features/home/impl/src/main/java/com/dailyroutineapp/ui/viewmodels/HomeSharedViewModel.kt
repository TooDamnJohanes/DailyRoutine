package com.dailyroutineapp.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.data.repositories.HabitsRepository
import com.dailyroutineapp.domain.model.Habit
import com.dailyroutineapp.domain.models.HomeScreenActions
import com.dailyroutineapp.domain.models.HomeScreenEditions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.State
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dailyroutineapp.core.models.Resource
import com.dailyroutineapp.datastore.HomeLocalDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val habitsHomeLocalDataStore: HomeLocalDataStore
) : ViewModel() {

    private val _allHabit = MutableStateFlow<Resource<List<Habit>>>(Resource.Loading(isLoading = true))
    val allHabits: StateFlow<Resource<List<Habit>>> = _allHabit

    private val _currentHabitTitle: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val currentHabitTitle: State<String> = _currentHabitTitle

    private val _currentHabitDescription: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val currentHabitDescription: State<String> = _currentHabitDescription

    private val _currentHabitTime: MutableState<Long> = mutableStateOf(ZERO_LONG)
    val currentHabitTime: State<Long> = _currentHabitTime

    private val _currentHabitWeekFrequency: MutableState<Int> = mutableStateOf(ZERO_INT)
    val currentHabitWeekFrequency: State<Int> = _currentHabitWeekFrequency

    private val _currentHabitPriority: MutableState<Priority> = mutableStateOf(Priority.NONE)
    val currentHabitPriority: State<Priority> = _currentHabitPriority

    private val _currentHabitSearchQuery: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val currentHabitSearchQuery: State<String> = _currentHabitSearchQuery

    private val _userPressSearchIcon: MutableState<Boolean> = mutableStateOf(false)
    val userPressSearchIcon: State<Boolean> = _userPressSearchIcon

    private val _expendedSortMenuDropDown: MutableState<Boolean> = mutableStateOf(false)
    val expendedSortMenuDropDown: State<Boolean> = _expendedSortMenuDropDown

    private val _expendedDeleteAllMenuDropDown: MutableState<Boolean> = mutableStateOf(false)
    val expendedDeleteAllMenuDropDown: State<Boolean> = _expendedDeleteAllMenuDropDown

    private val mockkHabit = Habit(
        id = 0,
        title = "",
        description = "",
        habitTime = 1L,
        habitWeekFrequency = 1,
        priority = Priority.HIGH
    )

    private val _currentHabit: MutableStateFlow<Habit> = MutableStateFlow(mockkHabit)
    val currentHabit: StateFlow<Habit> = _currentHabit

    fun onUiEvent(homeScreenActions: HomeScreenActions) {
        when (homeScreenActions) {
            is HomeScreenActions.Add -> handleAdd()
            is HomeScreenActions.Update -> handleUpdate(habitId = homeScreenActions.habitId)
            is HomeScreenActions.Delete -> handleDeleteHabit()
            is HomeScreenActions.DeleteAll -> handleDeleteAll()
            is HomeScreenActions.ClearHabitsSearchQuery -> handleClearHabitSearchQuery()
            is HomeScreenActions.NoAction -> handleNoAction()
            is HomeScreenActions.GetAllHabits -> handleGetAllHabits()
            is HomeScreenActions.GetHabit -> handleGetHabitById(habitId = homeScreenActions.habitId)
            is HomeScreenActions.SortHabits -> handleSortHabits(priority = homeScreenActions.priority)
            is HomeScreenActions.Search -> handleSearchQuery()
            is HomeScreenActions.StartTypeSearchQuery -> handleStartTypeSearchQuery()
            is HomeScreenActions.SortIconPressed -> handleSortIconPressed()
            is HomeScreenActions.DeleteAllIconPressed -> handleDeleteAllIconPressed()
        }
    }

    fun onUiEditionEvents(homeScreenEditions: HomeScreenEditions) {
        when (homeScreenEditions) {
            is HomeScreenEditions.UserEditingHabitTitle -> handleEditCurrentHabitTitle(
                homeScreenEditions.newHabitTitleValue
            )

            is HomeScreenEditions.UserEditingHabitDescription -> handleEditCurrentHabitDescription(
                homeScreenEditions.newHabitDescriptionValue
            )

            is HomeScreenEditions.UserEditingHabitTime -> handleEditCurrentHabitTime(
                homeScreenEditions.newHabitTimeValue
            )

            is HomeScreenEditions.UserEditingHabitFrequency -> handleEditCurrentHabitWeekFrequency(
                homeScreenEditions.newHabitFrequencyValue
            )

            is HomeScreenEditions.UserEditingHabitPriority -> handleEditCurrentHabitPriority(
                homeScreenEditions.newHabitPriorityValue
            )

            is HomeScreenEditions.UserEditingHabitSearchQuery -> handleEditCurrentHomeScreenSearchQuery(newHabitSearchQueryValue = homeScreenEditions.newHabitSearchQueryValue)
        }
    }

    fun getPriorityList(): List<Priority> {
        // return listOf(Priority.NONE, Priority.LOW, Priority.MEDIUM, Priority.HIGH)
        return listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
    }

    private fun handleAdd() {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(30) {
                habitsRepository.saveHabit(createHabit())
            }
        }
    }

    private fun createHabit(habitId: Int = Random.nextInt()): Habit {
        return Habit(
            id = habitId,
            title = mockHabitString(),
            description = mockHabitString(),
            habitTime = mockkHabitLong(),
            habitWeekFrequency = mockkHabitInt(),
            priority = mockkHabitPriority()
        )
    }

    private fun mockHabitString(): String {
        return Random.nextBytes(8).toString()
    }

    private fun mockkHabitLong(): Long {
        return Random.nextLong()
    }

    private fun mockkHabitInt(): Int {
        return Random.nextInt()
    }

    private fun mockkHabitPriority(): Priority {
        val randomIndex = Random.nextInt(3)
        return getPriorityList()[randomIndex]
    }

    private fun handleUpdate(habitId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.updateHabit(createHabit(habitId))
        }
    }

    private fun handleDeleteHabit() {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.deleteHabit(_currentHabit.value)
        }
    }

    private fun handleDeleteAll() {
        _allHabit.value.data?.let { habitsList ->
            viewModelScope.launch(Dispatchers.IO) {
                if (habitsRepository.deleteAllHabits(habitsList)) {
                    handleGetAllHabits()
                }
            }
        }
    }

    private fun handleGetAllHabits() {
        _allHabit.value = Resource.Loading(isLoading = true)
        handleSortHabits(getHabitsHomeScreenPriorityDataStore())
    }

    private fun setHabitListValue(habitsList: List<Habit>) {
        if (habitsList.isNotEmpty()) {
            _allHabit.value = Resource.Success(
                isLoading = false,
                data = habitsList
            )
        } else {
            _allHabit.value = Resource.Error(isLoading = false, message = EMPTY_STRING)
        }
    }

    private fun handleGetHabitById(habitId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.getHabitByHabitId(habitId = habitId.toString()).collect { habit ->
                habit?.let {
                    _currentHabit.value = it
                }
            }
        }
    }

    private fun handleSortHabits(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsHomeLocalDataStore.savePreference(key = HABITS_SORT_ORDER_DATA_STORE, value = priority.name)
            habitsRepository.sortHabitsByPriority(priority).collect { sortedHabitsList ->
                setHabitListValue(sortedHabitsList)
            }
        }
    }

    private fun handleSearchQuery() {
        _userPressSearchIcon.value = !_userPressSearchIcon.value
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.searchHabitByQuery(_currentHabitSearchQuery.value).collect { searchedHabits ->
                setHabitListValue(searchedHabits)
            }
        }
    }

    private fun handleEditCurrentHabitTitle(newHabitTitleValue: String) {
        _currentHabitTitle.value = newHabitTitleValue
    }

    private fun handleEditCurrentHabitDescription(newHabitDescriptionValue: String) {
        _currentHabitTitle.value = newHabitDescriptionValue
    }

    private fun handleEditCurrentHabitTime(newHabitHabitTimeValue: Long) {
        _currentHabitTime.value = newHabitHabitTimeValue
    }

    private fun handleEditCurrentHabitWeekFrequency(newHabitFrequencyValue: Int) {
        _currentHabitWeekFrequency.value = newHabitFrequencyValue
    }

    private fun handleEditCurrentHabitPriority(newHabitPriorityValue: Priority) {
        _currentHabitPriority.value = newHabitPriorityValue
    }

    private fun handleEditCurrentHomeScreenSearchQuery(newHabitSearchQueryValue: String) {
        _currentHabitSearchQuery.value = newHabitSearchQueryValue
    }

    private fun handleStartTypeSearchQuery() {
        _currentHabitSearchQuery.value = EMPTY_STRING
        _userPressSearchIcon.value = !_userPressSearchIcon.value
    }

    private fun handleClearHabitSearchQuery() {
        if (_currentHabitSearchQuery.value.isEmpty()) {
            _userPressSearchIcon.value = !_userPressSearchIcon.value
            handleGetAllHabits()
        } else {
            _currentHabitSearchQuery.value = EMPTY_STRING
        }
    }

    private fun handleSortIconPressed() {
        _expendedSortMenuDropDown.value = !_expendedSortMenuDropDown.value
    }

    private fun handleDeleteAllIconPressed() {
        _expendedDeleteAllMenuDropDown.value = !_expendedDeleteAllMenuDropDown.value
    }

    private fun getHabitsHomeScreenPriorityDataStore(): Priority {
        var habitsSortSelection = ""
        viewModelScope.launch {
            habitsSortSelection = habitsHomeLocalDataStore.getPreference(HABITS_SORT_ORDER_DATA_STORE) ?: EMPTY_STRING
        }
        return when (habitsSortSelection) {
            Priority.HIGH.name ->Priority.HIGH
            Priority.MEDIUM.name -> Priority.MEDIUM
            Priority.LOW.name -> Priority.LOW
            else -> Priority.NONE
        }
    }

    private fun handleNoAction() {

    }

    companion object {
        const val EMPTY_STRING = ""
        const val ZERO_LONG = 0L
        const val ZERO_INT = 0
        private const val HABITS_SORT_ORDER = "habits_sort_order"
        val HABITS_SORT_ORDER_DATA_STORE = stringPreferencesKey(HABITS_SORT_ORDER)
    }

}
