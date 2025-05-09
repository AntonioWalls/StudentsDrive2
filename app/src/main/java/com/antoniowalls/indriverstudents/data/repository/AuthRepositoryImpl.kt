package com.antoniowalls.indriverstudents.data.repository

import com.antoniowalls.indriverstudents.data.dataSource.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.data.util.HandleRequest
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import com.antoniowalls.indriverstudents.domain.util.Resource
import kotlinx.coroutines.flow.Flow

//una vez hecha la inyección de depdencias de authservice con su tipo de retorno, creamos el repositorio
class AuthRepositoryImpl(private val authService: AuthService, private val localDataStore: LocalDataStore): AuthRepository {
    override suspend fun login(email: String, password: String): Resource<AuthResponse> = HandleRequest.send(authService.login(email, password))

    override suspend fun register(user: User): Resource<AuthResponse> = HandleRequest.send(authService.register(user))

    override suspend fun saveSession(authResponse: AuthResponse) {
        localDataStore.save(authResponse)
    }

    override suspend fun updateSession(user: User) {
        localDataStore.update(user)
    }

    override suspend fun logout() {
        localDataStore.delete()
    }

    override fun getSessionData(): Flow<AuthResponse> = localDataStore.getData()
}