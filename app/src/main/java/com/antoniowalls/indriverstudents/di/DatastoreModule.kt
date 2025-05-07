package com.antoniowalls.indriverstudents.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.antoniowalls.indriverstudents.core.Config.AUTH_PREF
import com.antoniowalls.indriverstudents.data.local.datastore.LocalDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun providePreferencesDatastore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
            context.preferencesDataStoreFile(AUTH_PREF)
            }
        )
    @Provides
    fun provideLocalDatastore(dataStore: DataStore<Preferences>) = LocalDataStore(dataStore)
}