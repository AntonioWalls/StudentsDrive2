package com.antoniowalls.indriverstudents.presentation.screens.auth.register.mapper

import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterState

fun RegisterState.toUser(): User {
    return User(
        name = name,
        lastname = lastname,
        email = email,
        phone = phone,
        password = password,
    )
}