package com.dailyroutineapp.dailyroutine.di

import com.dailyroutineapp.data.datasources.HabitsLocalDataSource
import com.dailyroutineapp.data.datasources.HabitsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindHabitsLocalDataSource(
        habitsLocalDataSourceImpl: HabitsLocalDataSourceImpl
    ): HabitsLocalDataSource

}