package com.example.weatherapp.domain.model

data class CurrentWeatherModel(
    val temperature: TemperatureModel,
    val description: String?,
    val iconId: String?,
    val wind: WindModel
)
