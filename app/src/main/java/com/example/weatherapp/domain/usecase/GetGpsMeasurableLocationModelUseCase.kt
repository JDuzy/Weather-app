package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import javax.inject.Inject

class GetGpsMeasurableLocationModelUseCase @Inject constructor() {

    operator fun invoke() = WeatherMeasurableLocationModel(
        "Ubicación actual",
        location = null,
        isGPSLocation = true
    )
}