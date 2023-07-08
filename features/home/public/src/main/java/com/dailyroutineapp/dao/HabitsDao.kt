package com.dailyroutineapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.dailyroutineapp.core.dao.BaseUpsertDao
import com.dailyroutineapp.entity.Habit
import com.dailyroutineapp.entity.HabitDataBaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HabitsDao: BaseUpsertDao<Habit>() {
    /**
     * Select the correspondent habit for the given [habitId]
     *
     * @return the correspondent habit if exist
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName} " +
                "WHERE ${HabitDataBaseConstants.id} = :habitId"
    )
    abstract fun getHabitByHabitId(habitId: String): Flow<Habit>

    /**
     * Select all the habits
     *
     * @return all the habits storage
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName}"
    )
    abstract fun getAllHabits(): Flow<List<Habit>>

    /**
     * Select all the habits that contains the given [searchQuery] in their title/description
     *
     * @return the list of all habits, if some contains the given [searchQuery]
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName} " +
                "WHERE ${HabitDataBaseConstants.title} LIKE :searchQuery " +
                "OR ${HabitDataBaseConstants.description} LIKE :searchQuery"
    )
    abstract fun searchHabitByQuery(searchQuery: String): Flow<List<Habit>>

    /**
     * Sort the habits from low priority to high priority
     *
     * @return list of habits sorted by low priority
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName} " +
                "ORDER BY CASE WHEN ${HabitDataBaseConstants.priority} LIKE 'M%' THEN 1 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'H%' THEN 2 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'L' THEN 3 END"
    )
    abstract fun sortHabitsByLowPriority(): Flow<List<Habit>>

    /**
     * Sort the habits from medium priority to low priority
     *
     * @return list of habits sorted by medium priority
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName} " +
                "ORDER BY CASE WHEN ${HabitDataBaseConstants.priority} LIKE 'H%' THEN 1 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'L%' THEN 2 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'M' THEN 3 END"
    )
    abstract fun sortHabitsByMediumPriority(): Flow<List<Habit>>

    /**
     * Sort the habits from high priority to low priority
     *
     * @return list of habits sorted by high priority
     */
    @Query(
        "SELECT * FROM ${HabitDataBaseConstants.habitsTableName} " +
                "ORDER BY CASE WHEN ${HabitDataBaseConstants.priority} LIKE 'M%' THEN 1 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'L%' THEN 2 " +
                "WHEN ${HabitDataBaseConstants.priority} LIKE 'H' THEN 3 END"
    )
    abstract fun sortHabitsByHighPriority(): Flow<List<Habit>>
}
