package com.dailyroutineapp.dailyroutine.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvides {
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return DataStoreFactory.create(context = context)
    }

}

object DataStoreFactory {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "SETTINGS_DATA_STORE_NAME")

    fun create(context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}