package com.antoniowalls.indriverstudents.domain.useCases.auth

import com.antoniowalls.indriverstudents.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository)  {

    suspend operator fun invoke() = authRepository.logout()

}