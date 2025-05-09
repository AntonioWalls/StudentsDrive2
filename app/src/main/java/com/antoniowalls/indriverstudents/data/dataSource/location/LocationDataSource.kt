package com.antoniowalls.indriverstudents.data.dataSource.location

import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
//aquí estamos creando otra fuente de datos para obtener la ubicación del usuario
class LocationDataSource(private val context: Context) {
    private val fusedLocaltionClient = LocationServices.getFusedLocationProviderClient(context)

    @Suppress("MissingPermission")
    fun getLocationUpdates(callback: (position: LatLng) -> Unit){ //se crea una función para obtener la ubicación del usuario
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000).apply { //se crea una petición de ubicación
            setMinUpdateIntervalMillis(1000)
        }.build() //se ponen los intervalos de actualización y se retorna con el build

        val locationCallback = object : LocationCallback(){//se crea un objeto de tipo locationcallback
            override fun onLocationResult(locationResult: LocationResult) { //se crea una función para obtener la ubicación del usuario
                locationResult.locations.firstOrNull()?.let { //se obtiene la primera ubicación del usuario
                    callback(LatLng(it.latitude, it.longitude)) //se retorna la ubicación del usuario
                }
            }
        }
        fusedLocaltionClient.requestLocationUpdates(locationRequest, locationCallback, null) //se solicita la ubicación del usuario saltandonos los permisos
    }
}