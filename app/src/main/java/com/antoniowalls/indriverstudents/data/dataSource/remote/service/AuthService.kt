package com.antoniowalls.indriverstudents.data.dataSource.remote.service

import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
//aquí es donde tendremos los endpoints de nuestra API
    @FormUrlEncoded //este solo se usa cuando tengamos field en los parametros
    @POST("auth/login")
    suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String,
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register( //este proceso necesita ejecutarse en un proceso o entorno asincrono para no saturar la UI, SIEMPRE QUE EXISTA EL SUSPEND PASA ESTO
        @Body user: User
    ): Response<AuthResponse>

}