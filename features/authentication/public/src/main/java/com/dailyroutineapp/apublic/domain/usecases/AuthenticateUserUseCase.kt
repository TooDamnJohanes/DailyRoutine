package com.dailyroutineapp.apublic.domain.usecases

import com.dailyroutineapp.apublic.domain.models.AuthenticationType

interface AuthenticateUserUseCase {
    suspend operator fun invoke(
        authenticationType: AuthenticationType,
        email: String,
        password: String
    ): Boolean
}