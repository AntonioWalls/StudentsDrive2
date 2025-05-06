package com.antoniowalls.indriverstudents.domain.useCases.auth

import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)

}