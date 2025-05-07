package com.antoniowalls.indriverstudents.domain.repository

import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.util.Resource
import kotlinx.coroutines.flow.Flow

//aquí es donde tendremos las respuestas del servidor
interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<AuthResponse>
    suspend fun register(user: User): Resource<AuthResponse>
    suspend fun saveSession(authResponse: AuthResponse)
    suspend fun logout()
    fun getSessionData(): Flow<AuthResponse> //flow nunca se combina con suspend ya que flow se encarga de lo asincrono


}