package com.antoniowalls.indriverstudents.domain.useCases.location

import com.antoniowalls.indriverstudents.domain.repository.LocationRepository

class GetPlacePredictionsUseCase(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(query: String) = locationRepository.getPlacePredictions(query)

}