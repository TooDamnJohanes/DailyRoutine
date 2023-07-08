package com.dailyroutineapp.data.datasources

import com.dailyroutineapp.domain.model.Habit
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
interface HabitsLocalDataSource {

    fun saveHabit(habit: Habit): Boolean

    fun updateHabit(habit: Habit): Boolean

    fun deleteHabit(habit: Habit): Boolean

    fun deleteAllHabits(habitList: List<Habit>): Boolean

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitByHabitId(habitId: String): Flow<Habit?>

    fun searchHabitByQuery(searchQuery: String): Flow<List<Habit>>

    fun sortHabitsByLowPriority(): Flow<List<Habit>>

    fun sortHabitsByMediumPriority(): Flow<List<Habit>>

    fun sortHabitsByHighPriority(): Flow<List<Habit>>

}
