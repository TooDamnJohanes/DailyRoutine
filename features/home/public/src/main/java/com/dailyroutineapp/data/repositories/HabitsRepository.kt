package com.dailyroutineapp.data.repositories

import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.domain.model.Habit
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
interface HabitsRepository {

    fun saveHabit(habit: Habit): Boolean

    fun updateHabit(habit: Habit): Boolean

    fun deleteHabit(habit: Habit): Boolean

    fun deleteAllHabits(habitList: List<Habit>): Boolean

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitByHabitId(habitId: String): Flow<Habit?>

    fun searchHabitByQuery(searchQuery: String): Flow<List<Habit>>

    fun sortHabitsByPriority(priority: Priority): Flow<List<Habit>>

}
