package com.antoniowalls.indriverstudents.di

import com.antoniowalls.indriverstudents.core.Config
import com.antoniowalls.indriverstudents.data.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.data.remote.dataSource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ConnectionSpec
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
    fun provideOkHttpClient(datastore: LocalDataStore) = OkHttpClient.Builder()
        .connectionSpecs(listOf(ConnectionSpec.CLEARTEXT)) // Permite HTTP
        .addInterceptor {
            val token = runBlocking {
                datastore.getData().first().token
            }
            val newRequest = it.request().newBuilder().addHeader("Authorization", token ?: "").build()
            it.proceed(newRequest)
        }
        .build()

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

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService{
        return retrofit.create(UserService::class.java)
    } //este servicio ya lo podemos inyectar en el repositorio implementado o donde queramos

}