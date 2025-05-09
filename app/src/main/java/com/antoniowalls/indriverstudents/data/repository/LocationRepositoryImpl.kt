package com.antoniowalls.indriverstudents.data.repository

import com.antoniowalls.indriverstudents.data.dataSource.location.LocationDataSource
import com.antoniowalls.indriverstudents.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource): LocationRepository  {
    override fun getLocationUpdates(callback: (LatLng) -> Unit) {
        locationDataSource.getLocationUpdates(callback)
    }
}