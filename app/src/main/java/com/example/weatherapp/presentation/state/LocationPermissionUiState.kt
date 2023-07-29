package com.example.weatherapp.presentation.state

data class LocationPermissionUiState(
    val wasGranted: Boolean = false,
    val shouldShowRequestUi: Boolean = false,
    val isEnabled: Boolean = false
)


