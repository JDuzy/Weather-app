package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.usecase.GetWeatherMeasurableLocationsToShowUseCase
import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherapp.domain.usecase.GetGpsMeasurableLocationModelUseCase
import com.example.weatherapp.presentation.state.CurrentWeatherUiState
import com.example.weatherapp.presentation.state.LocationPermissionUiState
import com.example.weatherapp.presentation.state.LocationSelectorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherMeasurableLocationsToShow: GetWeatherMeasurableLocationsToShowUseCase,
    private val getGpsMeasurableLocationModel: GetGpsMeasurableLocationModelUseCase
) : ViewModel() {

    private val _currentWeatherSate: MutableStateFlow<CurrentWeatherUiState> =
        MutableStateFlow(CurrentWeatherUiState.Loading)
    val currentWeatherSate: StateFlow<CurrentWeatherUiState> = _currentWeatherSate.asStateFlow()

    private val _locationPermissionState: MutableStateFlow<LocationPermissionUiState> =
        MutableStateFlow(LocationPermissionUiState())
    val locationPermissionState = _locationPermissionState.asStateFlow()

    private val _locationSelectorUiState = MutableStateFlow(
        LocationSelectorUiState(
            locations = getWeatherMeasurableLocationsToShow(),
            selectedLocation = getGpsMeasurableLocationModel()
        )
    )
    val locationSelectorUiState = _locationSelectorUiState.asStateFlow()

    fun setLocationPermission(wasGranted: Boolean, shouldRequest: Boolean) {
        _locationPermissionState.update {
            it.copy(
                wasGranted = wasGranted,
                shouldShowRequestUi = shouldRequest
            )
        }
    }

    fun setLocationEnabled(enabled: Boolean) {
        _locationPermissionState.update {
            it.copy(isGPSEnabled = enabled)
        }
    }


    fun getGpsLocationCurrentWeather() {
        viewModelScope.launch {
            _currentWeatherSate.update {
                CurrentWeatherUiState.Loading
            }
            getCurrentWeatherFor(getGpsMeasurableLocationModel())
        }
    }
    fun selectLocation(
        weatherMeasurableLocation: WeatherMeasurableLocationModel
    ) {
        viewModelScope.launch {
            _currentWeatherSate.update {
                CurrentWeatherUiState.Loading
            }

            _locationSelectorUiState.update {
                it.copy(
                    isExpanded = false,
                    selectedLocation = weatherMeasurableLocation
                )
            }
            val locations = getWeatherMeasurableLocationsToShow().toMutableList()

            locations.remove(weatherMeasurableLocation)
            locations.add(0, weatherMeasurableLocation) // TODO this logic to a usecase?

            _locationSelectorUiState.update {
                it.copy(
                    locations = locations
                )
            }
            getCurrentWeatherFor(weatherMeasurableLocation)
        }
    }

    private suspend fun getCurrentWeatherFor(weatherMeasurableLocation: WeatherMeasurableLocationModel) {
        when (
            val resource =
                getCurrentWeatherUseCase(weatherMeasurableLocation)) {
            is Resource.Success -> {
                _currentWeatherSate.update {
                    resource.data?.let { currentWeatherModel ->
                        CurrentWeatherUiState.Success(currentWeatherModel)
                    } ?: CurrentWeatherUiState.Error
                }
            }

            is Resource.Error -> {
                _currentWeatherSate.update { CurrentWeatherUiState.Error }
            }
        }
    }

    fun expandLocationSelector() {
        _locationSelectorUiState.update { it.copy(isExpanded = true) }
    }
}