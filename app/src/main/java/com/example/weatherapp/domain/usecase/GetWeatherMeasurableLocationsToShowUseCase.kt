package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.model.LocationModel
import javax.inject.Inject

class GetWeatherMeasurableLocationsToShowUseCase @Inject constructor(
    private val getGpsMeasurableLocationModel: GetGpsMeasurableLocationModelUseCase
) {

    operator fun invoke() =
        listOf(
            getGpsMeasurableLocationModel(),
            WeatherMeasurableLocationModel(
                "Buenos aires",
                location = LocationModel(-34.613, -58.377)
            ),
            WeatherMeasurableLocationModel(
                "London",
                location = LocationModel(  51.512, -0.091)
            ),
            WeatherMeasurableLocationModel(
                "Montevideo",
                location = LocationModel(-34.903, -56.188)
            ),
            WeatherMeasurableLocationModel(
                "Sao Pablo",
                location = LocationModel(-23.682, -46.716)
            )
        )
}