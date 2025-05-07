package com.antoniowalls.indriverstudents.domain.useCases.auth

import javax.inject.Inject


//aquí almacena todos los casos de este modulo
data class AuthUseCases @Inject constructor (
    val login: LoginUseCase,
    val register: RegisterUseCase
)