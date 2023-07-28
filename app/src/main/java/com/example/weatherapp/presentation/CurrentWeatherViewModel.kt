package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.CityModel
import com.example.weatherapp.domain.usecase.GetCitiesToShowUseCase
import com.example.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase
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
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCitiesToShowUseCase: GetCitiesToShowUseCase
) : ViewModel() {

    private val _currentWeatherSate: MutableStateFlow<CurrentWeatherUiState> =
        MutableStateFlow(CurrentWeatherUiState.Loading)
    val currentWeatherSate: StateFlow<CurrentWeatherUiState> = _currentWeatherSate.asStateFlow()

    private val _locationPermissionState: MutableStateFlow<LocationPermissionUiState> =
        MutableStateFlow(LocationPermissionUiState())
    val locationPermissionState = _locationPermissionState.asStateFlow()

    private val _citiesToShow = MutableStateFlow(getCitiesToShowUseCase())
    val citiesToShow: StateFlow<List<CityModel>> = _citiesToShow.asStateFlow()

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

    fun getCurrentWeatherFor(cityModel: CityModel) = viewModelScope.launch {
        when (val resource = getCurrentWeatherUseCase(cityModel.location.lat, cityModel.location.lon)) {
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

    fun getActualLocationCurrentWeather() = viewModelScope.launch {
        val location = getCurrentLocationUseCase()
        _currentWeatherSate.update {
            location?.let {
                when(val resource = getCurrentWeatherUseCase(location.lat, location.lon)) {
                    is Resource.Success -> {
                        resource.data?.let { currentWeatherModel ->
                            CurrentWeatherUiState.Success(currentWeatherModel)
                        } ?: CurrentWeatherUiState.Error
                    }
                    is Resource.Error -> {
                        CurrentWeatherUiState.Error
                    }
                }
            } ?: CurrentWeatherUiState.Error
        }
    }
}