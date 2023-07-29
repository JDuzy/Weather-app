package com.example.weatherapp.domain.model

data class WeatherMeasurableLocationModel(
    val name: String,
    val location: LocationModel?,
    val isGPSLocation: Boolean = false
)
