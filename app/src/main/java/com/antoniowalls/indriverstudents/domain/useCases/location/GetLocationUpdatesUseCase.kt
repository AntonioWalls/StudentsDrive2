package com.antoniowalls.indriverstudents.domain.useCases.location

import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetLocationUpdatesUseCase(private val repository: LocationRepository) {

    operator fun invoke(callback:(position: LatLng) -> Unit ) = repository.getLocationUpdates(callback)

}