package com.antoniowalls.indriverstudents.data.remote.repository

import android.util.Log
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.UserService
import com.antoniowalls.indriverstudents.domain.model.ErrorResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.UserRepository
import com.antoniowalls.indriverstudents.domain.util.ErrorHelper
import com.antoniowalls.indriverstudents.domain.util.Resource
import java.io.File

class UserRepositoryImpl(private val userService: UserService): UserRepository {
    override suspend fun update(id: String, user: User, file: File?): Resource<User> {
        return try{
            val result = userService.update(id, user)
            if (result.isSuccessful){
                Log.d("UserRespositoryImpl", "Data Success ${result.body()}")
                Resource.Success(result.body()!!) //el !! sirve para los nulos
            } else{
                Log.d("UserRespositoryImpl", "Error en la petición")
                val errorResponse : ErrorResponse? = ErrorHelper.handleError(result.errorBody())
                Resource.Failure(errorResponse?.message?:"Error desconocido" )
            }

        } catch (e: Exception){
            Log.d("UserRespositoryImpl", "Message: ${e}")
            Log.d("UserRespositoryImpl", "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure(e.message ?: "Error desconocido" )
        }

    }
}