package com.dailyroutineapp.dailyroutine.di

import com.dailyroutineapp.apublic.data.repository.FirebaseAuthenticationRepository
import com.dailyroutineapp.data.repositories.HabitsRepository
import com.dailyroutineapp.data.repositories.HabitsRepositoryImpl
import com.dailyroutineapp.impl.data.repository.FirebaseAuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFirebaseAuthenticationRepository(
        firebaseAuthenticationRepositoryImpl: FirebaseAuthenticationRepositoryImpl
    ): FirebaseAuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindHabitsRepository(
        habitsRepositoryImpl: HabitsRepositoryImpl
    ): HabitsRepository
}
