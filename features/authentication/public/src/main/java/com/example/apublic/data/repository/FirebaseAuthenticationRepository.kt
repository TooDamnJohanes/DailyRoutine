package com.example.apublic.data.repository

interface FirebaseAuthenticationRepository {
    suspend fun loginUserWithEmailAndPassword(email: String, password: String): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean
}
