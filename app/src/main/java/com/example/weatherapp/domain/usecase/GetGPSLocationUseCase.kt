package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.location.LocationTracker
import javax.inject.Inject

class GetGPSLocationUseCase @Inject constructor(
    private val locationTracker: LocationTracker
) {

    suspend operator fun invoke() = locationTracker.getCurrentLocation()
}