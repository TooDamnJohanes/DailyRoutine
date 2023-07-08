package com.dailyroutineapp.data.datasources

import android.util.Log
import com.dailyroutineapp.dao.HabitsDao
import com.dailyroutineapp.data.mappers.asDomain
import com.dailyroutineapp.data.mappers.asEntity
import com.dailyroutineapp.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class HabitsLocalDataSourceImpl @Inject constructor(
    private val habitsDao: HabitsDao
): HabitsLocalDataSource {
    override fun saveHabit(habit: Habit): Boolean {
        return try {
            habitsDao.upsert(habit.asEntity)
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            false
        }
    }

    override fun updateHabit(habit: Habit): Boolean {
        return try {
            habitsDao.update(habit.asEntity)
            true
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            false
        }
    }

    override fun deleteHabit(habit: Habit): Boolean {
        return try {
            habitsDao.delete(habit.asEntity)
            true
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            false
        }
    }

    override fun deleteAllHabits(habitList: List<Habit>): Boolean {
        return try {
            habitsDao.deleteAll(habitList.map { it.asEntity })
            true
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            false
        }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return try {
            habitsDao.getAllHabits().map { habitsFlow ->
                habitsFlow.map { it.asDomain }
            }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow { emptyList<Habit>() }
        }
    }

    override fun getHabitByHabitId(habitId: String): Flow<Habit?> {
        return try {
            habitsDao.getHabitByHabitId(habitId).map { it.asDomain }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow{  }
        }
    }

    override fun searchHabitByQuery(searchQuery: String): Flow<List<Habit>> {
        return try {
            habitsDao.searchHabitByQuery(searchQuery).map { habitsFlow ->
                habitsFlow.map { it.asDomain }
            }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow { emptyList<Habit>() }
        }
    }

    override fun sortHabitsByLowPriority(): Flow<List<Habit>> {
        return try {
            habitsDao.sortHabitsByLowPriority().map { habitsFlow ->
                habitsFlow.map { it.asDomain }
            }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow { emptyList<Habit>() }
        }
    }

    override fun sortHabitsByMediumPriority(): Flow<List<Habit>> {
        return try {
            habitsDao.sortHabitsByMediumPriority().map { habitsFlow ->
                habitsFlow.map { it.asDomain }
            }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow { emptyList<Habit>() }
        }
    }

    override fun sortHabitsByHighPriority(): Flow<List<Habit>> {
        return try {
            habitsDao.sortHabitsByHighPriority().map { habitsFlow ->
                habitsFlow.map { it.asDomain }
            }
        } catch (t: Throwable) {
            Log.d(HABITS_LOCAL_DATASOURCE_ERROR, t.message.toString())
            flow { emptyList<Habit>() }
        }
    }

    companion object {
        const val HABITS_LOCAL_DATASOURCE_ERROR = "habits_datasource_error"
    }
}