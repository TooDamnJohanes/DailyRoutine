package com.dailyroutineapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.dailyroutineapp.core.models.Priority
import kotlin.random.Random

@Entity(
    tableName = HabitDataBaseConstants.habitsTableName,
    primaryKeys = [HabitDataBaseConstants.id]
)
data class Habit(
    @ColumnInfo(name = HabitDataBaseConstants.id) val id: Int = Random.nextInt(),
    @ColumnInfo(name = HabitDataBaseConstants.title) val title: String,
    @ColumnInfo(name = HabitDataBaseConstants.description) val description: String,
    @ColumnInfo(name = HabitDataBaseConstants.habitTime) val habitTime: Long,
    @ColumnInfo(name = HabitDataBaseConstants.habitWeekFrequency) val habitWeekFrequency: Int,
    @ColumnInfo(name = HabitDataBaseConstants.priority) val priority: Priority
)