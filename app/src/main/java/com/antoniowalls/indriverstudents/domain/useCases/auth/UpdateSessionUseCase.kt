package com.antoniowalls.indriverstudents.domain.useCases.auth

import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository

class UpdateSessionUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(user: User) = repository.updateSession(user)
}