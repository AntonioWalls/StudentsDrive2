package com.antoniowalls.indriverstudents.di

import android.content.Context
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.core.Config
import com.antoniowalls.indriverstudents.data.dataSource.local.datastore.LocalDataStore
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.AuthService
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.GoogleMapsService
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleMapsRetrofit

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
    @DefaultRetrofit
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
    @GoogleMapsRetrofit
    fun provideGoogleMapsRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Config.BASE_GOOGLE_MAPS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleMapsService(@GoogleMapsRetrofit retrofit: Retrofit): GoogleMapsService{
        return retrofit.create(GoogleMapsService::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthService(@DefaultRetrofit retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    } //este servicio ya lo podemos inyectar en el repositorio implementado o donde queramos

    @Provides
    @Singleton
    fun provideUserService(@DefaultRetrofit retrofit: Retrofit): UserService{
        return retrofit.create(UserService::class.java)
    } //este servicio ya lo podemos inyectar en el repositorio implementado o donde queramos

    @Provides
    fun provideApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.google_maps_key)
    }

}