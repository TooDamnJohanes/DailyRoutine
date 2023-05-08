package com.example.apublic.domain.usecases

import com.example.apublic.domain.models.AuthenticationType

interface AuthenticateUserUseCase {
    suspend operator fun invoke(
        authenticationType: AuthenticationType,
        email: String,
        password: String
    ): Boolean
}