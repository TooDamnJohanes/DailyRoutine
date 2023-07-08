package com.dailyroutineapp.dailyroutine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dailyroutineapp.dailyroutine.database.DailyRoutineDataBase.Companion.DAILY_ROUTINE_DATABASE_VERSION
import com.dailyroutineapp.dao.HabitsDao
import com.dailyroutineapp.entity.Habit

@Database(
    version = DAILY_ROUTINE_DATABASE_VERSION,
    entities = [Habit::class]
)
abstract class DailyRoutineDataBase: RoomDatabase() {

    abstract fun habitsDAO(): HabitsDao

    companion object {
        internal const val DAILY_ROUTINE_DATABASE_VERSION = 1
        const val DATABASE_NAME = "daily_routine_database"
    }
}