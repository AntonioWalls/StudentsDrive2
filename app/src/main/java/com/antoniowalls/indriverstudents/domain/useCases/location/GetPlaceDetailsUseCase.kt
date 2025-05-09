package com.antoniowalls.indriverstudents.domain.useCases.location

import com.antoniowalls.indriverstudents.domain.repository.LocationRepository

class GetPlaceDetailsUseCase(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(placeId: String) = locationRepository.getPlaceDetails(placeId)


}