package com.dailyroutineapp.data.repositories

import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.data.datasources.HabitsLocalDataSource
import com.dailyroutineapp.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HabitsRepositoryImpl @Inject constructor(
    private val habitsLocalDataSource: HabitsLocalDataSource
): HabitsRepository {
    override fun saveHabit(habit: Habit): Boolean {
        return habitsLocalDataSource.saveHabit(habit)
    }

    override fun updateHabit(habit: Habit): Boolean {
        return habitsLocalDataSource.updateHabit(habit)
    }

    override fun deleteHabit(habit: Habit): Boolean {
        return habitsLocalDataSource.deleteHabit(habit)
    }

    override fun deleteAllHabits(habitList: List<Habit>): Boolean {
        return habitsLocalDataSource.deleteAllHabits(habitList)
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitsLocalDataSource.getAllHabits()
    }

    override fun getHabitByHabitId(habitId: String): Flow<Habit?> {
        return habitsLocalDataSource.getHabitByHabitId(habitId)
    }

    override fun searchHabitByQuery(searchQuery: String): Flow<List<Habit>> {
        return habitsLocalDataSource.searchHabitByQuery(searchQuery)
    }

    override fun sortHabitsByPriority(priority: Priority): Flow<List<Habit>> {
        return when(priority) {
            Priority.HIGH -> habitsLocalDataSource.sortHabitsByHighPriority()
            Priority.MEDIUM -> habitsLocalDataSource.sortHabitsByMediumPriority()
            Priority.LOW -> habitsLocalDataSource.sortHabitsByLowPriority()
            Priority.NONE -> habitsLocalDataSource.getAllHabits()
        }
    }
}
