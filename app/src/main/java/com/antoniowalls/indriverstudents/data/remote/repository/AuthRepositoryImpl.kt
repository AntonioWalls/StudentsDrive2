package com.antoniowalls.indriverstudents.data.remote.repository

import android.util.Log
import com.antoniowalls.indriverstudents.data.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.ErrorResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.AuthRepository
import com.antoniowalls.indriverstudents.domain.util.ErrorHelper
import com.antoniowalls.indriverstudents.domain.util.Resource
import kotlinx.coroutines.flow.Flow

//una vez hecha la inyección de depdencias de authservice con su tipo de retorno, creamos el repositorio
class AuthRepositoryImpl(private val authService: AuthService, private val localDataStore: LocalDataStore): AuthRepository {
    override suspend fun login(email: String, password: String): Resource<AuthResponse> {
        return try{
            val result = authService.login(email, password)
            if (result.isSuccessful){
                Log.d("AuthRespositoryImpl", "Data Success ${result.body()}")
                Resource.Success(result.body()!!) //el !! sirve para los nulos
            } else{
                Log.d("AuthRespositoryImpl", "Error en la petición")
                val errorResponse : ErrorResponse? = ErrorHelper.handleError(result.errorBody())
                Resource.Failure(errorResponse?.message?:"Error desconocido" )
            }

        } catch (e: Exception){
            Log.d("AuthRespositoryImpl", "Message: ${e}")
            Log.d("AuthRespositoryImpl", "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure(e.message ?: "Error desconocido" )
        }
    }

    override suspend fun register(user: User): Resource<AuthResponse> {
        return try{
            val result = authService.register(user)
            if (result.isSuccessful){
                Log.d("AuthRespositoryImpl", "Data Success ${result.body()}")
                Resource.Success(result.body()!!) //el !! sirve para los nulos
            } else{
                Log.d("AuthRespositoryImpl", "Error en la petición")
                val errorResponse : ErrorResponse? = ErrorHelper.handleError(result.errorBody())
                Resource.Failure(errorResponse?.message?:"Error desconocido" )
            }

        } catch (e: Exception){
            Log.d("AuthRespositoryImpl", "Message: ${e}")
            Log.d("AuthRespositoryImpl", "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure(e.message ?: "Error desconocido" )
        }
    }

    override suspend fun saveSession(authResponse: AuthResponse) {
        localDataStore.save(authResponse)
    }

    override suspend fun logout() {
        localDataStore.delete()
    }

    override fun getSessionData(): Flow<AuthResponse> = localDataStore.getData()
}