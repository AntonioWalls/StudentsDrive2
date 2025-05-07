package com.antoniowalls.indriverstudents.domain.useCases.auth

import com.antoniowalls.indriverstudents.domain.repository.AuthRepository

class GetSessionDataUseCase(private val repository: AuthRepository) {

     operator fun invoke() = repository.getSessionData()

}