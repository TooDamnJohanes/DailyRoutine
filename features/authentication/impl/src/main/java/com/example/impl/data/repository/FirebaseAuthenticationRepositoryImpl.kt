package com.example.impl.data.repository

import android.util.Log
import com.example.apublic.data.repository.FirebaseAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseAuthenticationRepositoryImpl @Inject constructor() :
    FirebaseAuthenticationRepository {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    override suspend fun loginUserWithEmailAndPassword(email: String, password: String): Boolean {
        return try {
            var isTaskSuccessful = true
            firebaseAuth
                .signInWithEmailAndPassword(
                    email,
                    password
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(FIREBASE_SING_IN_USER, task.result.toString())
                    } else {
                        isTaskSuccessful = task.isSuccessful
                        Log.d(FIREBASE_SING_IN_USER_ERROR, task.result.toString())
                    }
                }
            isTaskSuccessful
        } catch (t: Throwable) {
            Log.d(FIREBASE_SING_IN_USER_ERROR, t.message.toString())
            false
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean {
        return try {
            var isTaskSuccessful = true
            firebaseAuth
                .createUserWithEmailAndPassword(
                    email,
                    password
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(FIREBASE_CREATE_USER, task.result.toString())
                    } else {
                        isTaskSuccessful = task.isSuccessful
                        Log.d(FIREBASE_CREATE_USER_ERROR, task.result.toString())
                    }
                }
            isTaskSuccessful
        } catch (t: Throwable) {
            Log.d(FIREBASE_SING_IN_USER_ERROR, t.message.toString())
            false
        }
    }

    companion object {
        const val FIREBASE_SING_IN_USER = "firebase sign in user"
        const val FIREBASE_SING_IN_USER_ERROR = "sign in user - error"
        const val FIREBASE_CREATE_USER = "firebase create user"
        const val FIREBASE_CREATE_USER_ERROR = "create user - error"
    }
}
