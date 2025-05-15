package com.antoniowalls.indriverstudents.domain.useCases.location

import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetRouteUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(origin: LatLng, destination: LatLng) = repository.getRoute(origin, destination)
}