package com.dailyroutineapp.dailyroutine.di

import com.dailyroutineapp.apublic.domain.usecases.AuthenticateUserUseCase
import com.dailyroutineapp.impl.domain.usecases.AuthenticateUserUseCaseImpl
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
