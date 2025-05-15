package com.antoniowalls.indriverstudents.presentation.screens.driver.mapMyLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.useCases.location.LocationUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverMapMyLocationViewModel@Inject constructor(private val locationUseCases: LocationUseCases) :ViewModel()  {
    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> get() = _location

    fun startLocationUpdates()=viewModelScope.launch {
        locationUseCases.getLocationUpdates { position ->
            _location.value = position
        }
    }
}