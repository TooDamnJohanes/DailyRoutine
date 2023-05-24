package com.dailyroutineapp.impl.data.repository

import android.util.Log
import com.dailyroutineapp.apublic.data.repository.FirebaseAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class FirebaseAuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthenticationRepository {
    override suspend fun loginUserWithEmailAndPassword(email: String, password: String): Boolean {
        val isTaskSuccessful = CompletableDeferred<Boolean>()
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(FIREBASE_SING_IN_USER, task.result.toString())
                        isTaskSuccessful.complete(true)
                    } else {
                        Log.d(FIREBASE_SING_IN_USER_ERROR, task.exception.toString())
                        isTaskSuccessful.complete(false)
                    }
                }
            return isTaskSuccessful.await()
        } catch (t: Throwable) {
            Log.d(FIREBASE_SING_IN_USER_ERROR, t.message.toString())
            return false
        }
    }


    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean {
        return try {
            val isTaskSuccessful = CompletableDeferred<Boolean>()
            firebaseAuth
                .createUserWithEmailAndPassword(
                    email,
                    password
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        isTaskSuccessful.complete(true)
                        Log.d(FIREBASE_CREATE_USER, task.result.toString())
                    } else {
                        isTaskSuccessful.complete(false)
                        Log.d(FIREBASE_CREATE_USER_ERROR, task.exception.toString())
                    }
                }
            isTaskSuccessful.await()
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
