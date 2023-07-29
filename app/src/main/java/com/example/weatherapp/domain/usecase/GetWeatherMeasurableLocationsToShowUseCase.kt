package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.model.LocationModel
import javax.inject.Inject

class GetWeatherMeasurableLocationsToShowUseCase @Inject constructor() {

    operator fun invoke() =
        listOf(
            WeatherMeasurableLocationModel(
                "Ubicación actual",
                location = null
            ),
            WeatherMeasurableLocationModel(
                "Montevideo",
                location = LocationModel(-34.903, -56.188)
            ),
            WeatherMeasurableLocationModel(
                "Londres",
                location = LocationModel(  51.512, -0.091)
            ),
            WeatherMeasurableLocationModel(
                "San Pablo",
                location = LocationModel(-23.682, -46.716)
            ),
            WeatherMeasurableLocationModel(
                "Buenos aires",
                location = LocationModel(-34.613, -58.377)
            )
        )
}