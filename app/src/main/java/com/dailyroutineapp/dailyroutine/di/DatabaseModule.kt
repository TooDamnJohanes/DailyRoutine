package com.dailyroutineapp.dailyroutine.di

import android.content.Context
import androidx.room.Room
import com.dailyroutineapp.dailyroutine.database.DailyRoutineDataBase
import com.dailyroutineapp.dailyroutine.database.DailyRoutineDataBase.Companion.DATABASE_NAME
import com.dailyroutineapp.dao.HabitsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ): DailyRoutineDataBase {
        return Room.databaseBuilder(
            context,
            DailyRoutineDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideHabitsDao(
        dailyRoutineDatabase: DailyRoutineDataBase
    ): HabitsDao {
        return dailyRoutineDatabase.habitsDAO()
    }

}