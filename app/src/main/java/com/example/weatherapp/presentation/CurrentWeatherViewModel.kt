package com.example.weatherapp.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
): ViewModel() {

    private val _currentWeatherSate = MutableStateFlow(CurrentWeatherUiState.Loading)
    val currentWeatherSate: StateFlow<CurrentWeatherUiState> = _currentWeatherSate.asStateFlow()

    private val _locationPermissionState: MutableStateFlow<LocationPermissionUiState> = MutableStateFlow(LocationPermissionUiState())
    val locationPermissionState = _locationPermissionState.asStateFlow()

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
            it.copy(isEnabled = enabled)
        }
    }

}