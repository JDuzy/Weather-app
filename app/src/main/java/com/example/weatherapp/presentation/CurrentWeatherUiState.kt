package com.example.weatherapp.presentation

import com.example.weatherapp.domain.model.CurrentWeatherModel

sealed class CurrentWeatherUiState {

    object Loading : CurrentWeatherUiState()

    data class Success (
        val currentWeatherModel: CurrentWeatherModel
    )

    object Error : CurrentWeatherUiState()

}
