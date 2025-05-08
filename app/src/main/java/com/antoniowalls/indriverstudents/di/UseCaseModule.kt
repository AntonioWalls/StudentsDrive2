package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import com.antoniowalls.indriverstudents.domain.repository.UserRepository
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCases
import com.antoniowalls.indriverstudents.domain.useCases.auth.GetSessionDataUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.LoginUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.LogoutUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.RegisterUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.SaveSessionUseCase
import com.antoniowalls.indriverstudents.domain.useCases.auth.UpdateSessionUseCase
import com.antoniowalls.indriverstudents.domain.useCases.user.UserUpdateUseCase
import com.antoniowalls.indriverstudents.domain.useCases.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//todo lo relacionado a los casos de uso
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCases(
        login = LoginUseCase(authRepository),
        register = RegisterUseCase(authRepository),
        saveSession = SaveSessionUseCase(authRepository),
        getSessionData = GetSessionDataUseCase(authRepository),
        logout = LogoutUseCase(authRepository),
        updateSessionUseCase = UpdateSessionUseCase(authRepository)
    )

   @Provides
   fun provideUserUseCases(userRepository: UserRepository) = UserUseCases(
       update = UserUpdateUseCase(userRepository)
   )
}