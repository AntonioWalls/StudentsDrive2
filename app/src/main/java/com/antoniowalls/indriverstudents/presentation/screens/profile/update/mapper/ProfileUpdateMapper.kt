package com.antoniowalls.indriverstudents.presentation.screens.profile.update.mapper

import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.presentation.screens.profile.update.ProfileUpdateState

fun ProfileUpdateState.toUser(): User {
    return User(
        name = name,
        lastname = lastname,
        phone = phone,
        image = image
    )
}