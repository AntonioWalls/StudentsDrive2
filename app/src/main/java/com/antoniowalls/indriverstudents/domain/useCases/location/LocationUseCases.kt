package com.antoniowalls.indriverstudents.domain.useCases.location

data class LocationUseCases (
    val getLocationUpdates: GetLocationUpdatesUseCase,
    val getPlacePredictions: GetPlacePredictionsUseCase,
    val getPlaceDetails: GetPlaceDetailsUseCase,
    val getPlaceFromLatLng: GetPlaceFromLatLngUseCase,
    val getRoute: GetRouteUseCase
)