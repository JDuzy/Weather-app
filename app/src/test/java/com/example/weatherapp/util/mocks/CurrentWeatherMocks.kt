package com.example.weatherapp.util.mocks

import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.TemperatureModel
import com.example.weatherapp.domain.model.WindModel

object CurrentWeatherMocks {

    val gpsCurrentWeather = CurrentWeatherModel(
        temperature = TemperatureModel(
            actualTemp = 15,
            minTemp = 10,
            maxTemp = 20,
            feelsLike = 16,
        ),
        description = "Cloudy",
        iconId = "02d",
        wind = WindModel(speed = 10.0, degree = 20.0)
    )

    val genericCurrentWeatherModel = CurrentWeatherModel(
        temperature = TemperatureModel(
            actualTemp = 10,
            minTemp = 8,
            maxTemp = 12,
            feelsLike = 10,
        ),
        description = "Thunders",
        iconId = "04d",
        wind = WindModel(speed = 10.0, degree = 20.0)
    )

    val errorMessage = "Error getting current weather"
}
