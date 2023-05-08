package com.example.dailyroutine.di

import com.example.apublic.domain.usecases.AuthenticateUserUseCase
import com.example.impl.domain.usecases.AuthenticateUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindAuthenticateUserUseCase(
        authenticateUserUseCaseImpl: AuthenticateUserUseCaseImpl
    ): AuthenticateUserUseCase
}
