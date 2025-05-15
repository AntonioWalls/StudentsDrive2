package com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.PlacePrediction
import com.antoniowalls.indriverstudents.domain.useCases.location.LocationUseCases
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMapSearcherViewModel @Inject constructor(private val locationUseCases: LocationUseCases): ViewModel() {
    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> get() = _location

    private val _placePredictions = MutableStateFlow<List<PlacePrediction>>(emptyList())
    val placePredictions: StateFlow<List<PlacePrediction>> get() = _placePredictions

    private val _originPlace = MutableStateFlow<Place?>(null)
    val originPlace: StateFlow<Place?> get() = _originPlace

    private val _destinationPlace = MutableStateFlow<Place?>(null)
    val destinationPlace: StateFlow<Place?> get() = _destinationPlace

    private val _route = MutableStateFlow<List<LatLng>?>(null)
    val route: StateFlow<List<LatLng>?> get() = _route

    var isInteractingWithMap by mutableStateOf(false)

    fun startLocationUpdates()=viewModelScope.launch {
        locationUseCases.getLocationUpdates { position ->
            _location.value = position
        }
    }

    fun getPlacePredictions(query: String) = viewModelScope.launch {
        _placePredictions.value = locationUseCases.getPlacePredictions(query)
    }

    fun getPlaceDetails(placeId: String,isOrigin: Boolean, onPlaceSelected: (place:Place) -> Unit) = viewModelScope.launch {
        val place = locationUseCases.getPlaceDetails(placeId)
        if(isOrigin){
            _originPlace.value = place
        }else{
            _destinationPlace.value = place
        }
        onPlaceSelected(place)
    }

    fun getPlaceFromLatLng(latLng: LatLng) = viewModelScope.launch {
        val place = locationUseCases.getPlaceFromLatLng(latLng)
        _originPlace.value = place

    }

    fun getRoute() = viewModelScope.launch {
        if(originPlace.value != null && destinationPlace.value != null){
            _route.value = locationUseCases.getRoute(originPlace.value!!.latLng!!, destinationPlace.value!!.latLng!!)
        }
    }


}