package com.antoniowalls.indriverstudents.data.dataSource.remote.service

import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
//aquí es donde tendremos los endpoints de nuestra API
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

}