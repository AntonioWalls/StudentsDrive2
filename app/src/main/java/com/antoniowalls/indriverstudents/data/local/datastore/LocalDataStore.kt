package com.antoniowalls.indriverstudents.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.antoniowalls.indriverstudents.core.Config.AUTH_KEY
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class LocalDataStore(private val datastore: DataStore<Preferences>){

    //Método para guardar el token en el datastore
    suspend fun save(authResponse: AuthResponse){
        val datastoreKey = stringPreferencesKey(AUTH_KEY)
        datastore.edit { pref ->
            pref[datastoreKey] = authResponse.toJson()
        }
    }

    suspend fun update(user: User){
        val datastoreKey = stringPreferencesKey(AUTH_KEY)
        val authResponse = runBlocking {
            getData().first() //los datos de sesión que tenemos hasta el momento
        }
        authResponse.user?.name = user.name
        authResponse.user?.lastname = user.lastname
        authResponse.user?.phone = user.phone
        if(!user.image.isNullOrBlank()){
            authResponse.user?.image = user.image
        }

        datastore.edit { pref ->
            pref[datastoreKey] = authResponse.toJson()
        }
    }

    //método para eliminar el token del datastore
    suspend fun delete(){
        val datastoreKey = stringPreferencesKey(AUTH_KEY)
        datastore.edit { pref ->
            pref.remove(datastoreKey)
        }

    }
    //método para obtener el token del datastore
    fun getData(): Flow<AuthResponse>{
        val datastoreKey = stringPreferencesKey(AUTH_KEY)
        return datastore.data.map { pref ->
            if(pref[datastoreKey] != null){
                AuthResponse.fromJson(pref[datastoreKey]!!)
            } else{
                AuthResponse()
            }
        }
    }

}