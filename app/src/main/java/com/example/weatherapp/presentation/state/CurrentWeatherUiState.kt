package com.example.weatherapp.presentation.state

import com.example.weatherapp.domain.model.CurrentWeatherModel

sealed class CurrentWeatherUiState {

    object Loading : CurrentWeatherUiState()
    object FirstTime : CurrentWeatherUiState()

    data class Success (
        val currentWeatherModel: CurrentWeatherModel
    ): CurrentWeatherUiState()

    object Error : CurrentWeatherUiState()

}
