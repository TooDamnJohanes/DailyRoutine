package com.example.dailyroutine.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariablesModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return Firebase.auth
    }

}
