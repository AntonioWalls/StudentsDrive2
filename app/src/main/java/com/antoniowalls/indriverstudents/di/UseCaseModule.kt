package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//todo lo relacionado a los casos de uso
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(
        login = LoginUseCase(authRepository),
    )
}