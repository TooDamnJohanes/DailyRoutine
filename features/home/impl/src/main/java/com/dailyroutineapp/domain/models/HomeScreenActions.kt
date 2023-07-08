package com.dailyroutineapp.domain.models

import com.dailyroutineapp.core.models.Priority

sealed class HomeScreenActions(val habitId: Int = 0, val priority: Priority = Priority.NONE) {
    class Add : HomeScreenActions()
    class Update(habitId: Int) : HomeScreenActions(habitId)
    class Delete : HomeScreenActions()
    class DeleteAll : HomeScreenActions()
    class ClearHabitsSearchQuery : HomeScreenActions()
    class NoAction : HomeScreenActions()
    class GetAllHabits : HomeScreenActions()
    class GetHabit(habitId: Int) : HomeScreenActions(habitId)
    class SortHabits(priority: Priority) : HomeScreenActions(priority = priority)
    class Search : HomeScreenActions()
    class StartTypeSearchQuery : HomeScreenActions()
    class SortIconPressed : HomeScreenActions()
    class DeleteAllIconPressed : HomeScreenActions()
}

sealed class HomeScreenEditions {
    class UserEditingHabitTitle(val newHabitTitleValue: String) : HomeScreenEditions()
    class UserEditingHabitDescription(val newHabitDescriptionValue: String) : HomeScreenEditions()
    class UserEditingHabitTime(val newHabitTimeValue: Long) : HomeScreenEditions()
    class UserEditingHabitFrequency(val newHabitFrequencyValue: Int) : HomeScreenEditions()
    class UserEditingHabitPriority(val newHabitPriorityValue: Priority) : HomeScreenEditions()
    class UserEditingHabitSearchQuery(val newHabitSearchQueryValue: String) : HomeScreenEditions()
}
