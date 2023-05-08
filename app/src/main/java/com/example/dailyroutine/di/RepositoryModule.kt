package com.example.dailyroutine.di

import com.example.apublic.data.repository.FirebaseAuthenticationRepository
import com.example.impl.data.repository.FirebaseAuthenticationRepositoryImpl
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
}
