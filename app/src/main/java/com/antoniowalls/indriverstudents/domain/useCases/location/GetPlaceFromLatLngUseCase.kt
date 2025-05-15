package com.antoniowalls.indriverstudents.domain.useCases.location

import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place

class GetPlaceFromLatLngUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(latLng: LatLng): Place? = repository.getPlaceFromLatLng(latLng)

}