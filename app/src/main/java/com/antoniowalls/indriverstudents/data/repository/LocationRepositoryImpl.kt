package com.antoniowalls.indriverstudents.data.repository

import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.data.dataSource.location.LocationDataSource
import com.antoniowalls.indriverstudents.data.dataSource.remote.service.GoogleMapsService
import com.antoniowalls.indriverstudents.data.util.PolylineDrawer
import com.antoniowalls.indriverstudents.domain.model.DirectionsResponse
import com.antoniowalls.indriverstudents.domain.model.PlacePrediction
import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource, private val placesClient: PlacesClient, private val geocoder: Geocoder, private val googleMapsService: GoogleMapsService, private val apiKey: String): LocationRepository  {
    override fun getLocationUpdates(callback: (LatLng) -> Unit) {
        locationDataSource.getLocationUpdates(callback)
    }

    override suspend fun getPlacePredictions(query: String): List<PlacePrediction> {
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
        return suspendCancellableCoroutine { continuation ->
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                val predictions = response.autocompletePredictions.map { prediction ->
                    PlacePrediction(
                        placeId = prediction.placeId,
                        fullText = prediction.getFullText(null).toString()
                    )
                }
                continuation.resume(predictions) {}
            }.addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
        }
    }

    override suspend fun getPlaceDetails(placeId: String): Place {
        val request = FetchPlaceRequest.builder(
            placeId,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
        ).build()
        return suspendCancellableCoroutine { continuation ->
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    continuation.resume(response.place) {}
                }.addOnFailureListener { e->
                    continuation.resumeWithException(e)
                }
        }
    }

    override suspend fun getPlaceFromLatLng(latLng: LatLng): Place? {
        return withContext(Dispatchers.IO) {
            try {
                val addresses: List<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addresses != null && addresses.isNotEmpty()){
                    val address = addresses[0]
                    Place
                        .builder()
                        .setName(address.featureName ?: "Dirección desconocida")
                        .setAddress(address.getAddressLine(0) ?: "Sin dirección")
                        .setLatLng(LatLng(address.latitude, address.longitude))
                        .build()
                }else{
                    null
                }
            }catch (e: IOException){
                Log.d("LocationRepositoryImpl", "Error: ${e.message}")
                null
            }
        }
    }

    override suspend fun getRoute(origin: LatLng, destination: LatLng): List<LatLng>? {
        try {
            val response = googleMapsService.getDirections(
                origin = "${origin.latitude},${origin.longitude}",
                destination = "${destination.latitude},${destination.longitude}",
                apiKey = apiKey
            )
            Log.d("LocationRepositoryImpl", "Origin: ${origin.latitude},${origin.longitude}")
            Log.d("LocationRepositoryImpl", "Destination: ${destination.latitude},${destination.longitude}")
            Log.d("LocationRepositoryImpl", "Response: ${response}")
            return parseRoute(response)
        }catch (e: Exception){
            Log.d("LocationRepositoryImpl", "Error: ${e.message}")
            return null
        }
    }

    private fun parseRoute(response: DirectionsResponse): List<LatLng>{
        val route = mutableListOf<LatLng>()
        val points = response.routes.firstOrNull()?.overviewPolyline?.points ?: return route
        val decodePoints = PolylineDrawer.decodePoly(points)
        decodePoints.forEach { point ->
            route.add(LatLng(point.latitude, point.longitude))
        }
        return route
    }

}