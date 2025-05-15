package com.antoniowalls.indriverstudents.domain.repository

import androidx.compose.ui.input.pointer.PointerId
import com.antoniowalls.indriverstudents.domain.model.PlacePrediction
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import okhttp3.Call

interface LocationRepository {
    fun getLocationUpdates(call: (position: LatLng) -> Unit)
    suspend fun getPlacePredictions(query: String): List<PlacePrediction>
    suspend fun getPlaceDetails(placeId: String): Place
    suspend fun getPlaceFromLatLng(latLng: LatLng): Place?
    suspend fun getRoute(origin: LatLng, destination: LatLng): List<LatLng>?
}