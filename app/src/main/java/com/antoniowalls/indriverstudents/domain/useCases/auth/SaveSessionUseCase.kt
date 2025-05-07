package com.antoniowalls.indriverstudents.domain.useCases.auth

import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository

class SaveSessionUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(authResponse: AuthResponse) = repository.saveSession(authResponse)

}