package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.data.dataSource.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.dataSource.location.LocationDataSource
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.UserService
import com.antoniowalls.indriverstudents.data.repository.AuthRepositoryImpl
import com.antoniowalls.indriverstudents.data.repository.LocationRepositoryImpl
import com.antoniowalls.indriverstudents.data.repository.UserRepositoryImpl
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.antoniowalls.indriverstudents.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(authService: AuthService, localDataStore: LocalDataStore): AuthRepository = AuthRepositoryImpl(authService, localDataStore)

    @Provides
    fun provideUserRepository(userService: UserService): UserRepository = UserRepositoryImpl(userService)

    @Provides
    fun provideLocationRepository(LocationDataSource: LocationDataSource): LocationRepository = LocationRepositoryImpl(LocationDataSource)

}