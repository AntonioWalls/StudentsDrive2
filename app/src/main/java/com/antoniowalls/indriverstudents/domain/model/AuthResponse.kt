package com.antoniowalls.indriverstudents.domain.model

data class AuthResponse (
    val user: User? = null,
    val token: String? = null
)