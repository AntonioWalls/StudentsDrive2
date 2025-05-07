package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.data.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.data.remote.repository.AuthRepositoryImpl
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(authService: AuthService, localDataStore: LocalDataStore): AuthRepository = AuthRepositoryImpl(authService, localDataStore)

}