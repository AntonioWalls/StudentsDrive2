package com.antoniowalls.indriverstudents.domain.repository

import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.util.Resource

//aquí es donde tendremos las respuestas del servidor
interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<AuthResponse>


}