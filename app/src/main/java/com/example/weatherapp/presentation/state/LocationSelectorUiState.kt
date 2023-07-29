package com.example.weatherapp.presentation.state

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel

data class LocationSelectorUiState(
    val isExpanded: Boolean = false,
    val selectedLocation: WeatherMeasurableLocationModel,
    val locations: List<WeatherMeasurableLocationModel>
)
