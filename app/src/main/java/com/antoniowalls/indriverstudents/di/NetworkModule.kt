package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.core.Config
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//aquí básicamente se hace la inyección de dependencias y creamos un cliente tipo postman o thunder client

    @Provides
    @Singleton
    fun provideOkHttpClient()= OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    } //este servicio ya lo podemos inyectar en el repositorio implementado o donde queramos

}