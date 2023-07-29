package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.model.LocationModel
import javax.inject.Inject

class GetWeatherMeasurableLocationsToShowUseCase @Inject constructor() {

    operator fun invoke() =
        listOf(
            WeatherMeasurableLocationModel(
                "Montevideo",
                location = LocationModel(22.2, 22.2)
            ),
            WeatherMeasurableLocationModel(
                "Londres",
                location = LocationModel(22.2, 22.2)
            ),
            WeatherMeasurableLocationModel(
                "San Pablo",
                location = LocationModel(22.2, 22.2)
            ),
            WeatherMeasurableLocationModel(
                "Buenos aires",
                location = LocationModel(22.2, 22.2)
            ),
            WeatherMeasurableLocationModel(
                "Ubicación actual",
                location = null
            )
        )
}