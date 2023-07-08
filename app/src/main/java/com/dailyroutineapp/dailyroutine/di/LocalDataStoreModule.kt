package com.dailyroutineapp.dailyroutine.di

import com.dailyroutineapp.datastore.HomeLocalDataStore
import com.dailyroutineapp.datastore.HomeLocalDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataStoreModule {
    @Binds
    @Singleton
    abstract fun bindHabitHomeScreenDataStore(
        homeLocalDataStoreImpl: HomeLocalDataStoreImpl
    ): HomeLocalDataStore
}