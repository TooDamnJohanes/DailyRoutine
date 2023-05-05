package com.example.impl.domain.usecases

import com.example.apublic.data.repository.FirebaseAuthenticationRepository
import com.example.apublic.domain.models.AuthenticationType
import com.example.apublic.domain.models.AuthenticationType.LOGIN
import com.example.apublic.domain.models.AuthenticationType.SINGUP
import com.example.apublic.domain.usecases.AuthenticateUserUseCase
import javax.inject.Inject

class AuthenticateUserUseCaseImpl @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) : AuthenticateUserUseCase {
    override suspend fun invoke(
        authenticationType: AuthenticationType,
        email: String,
        password: String
    ): Boolean {
        return when (authenticationType) {
            LOGIN -> {
                firebaseAuthenticationRepository.loginUserWithEmailAndPassword(
                    email = email,
                    password = password
                )
            }

            SINGUP -> {
                firebaseAuthenticationRepository.createUserWithEmailAndPassword(
                    email = email,
                    password = password
                )
            }
        }
    }
}
