package com.example.weatherapp.presentation

data class LocationPermissionUiState(
    val wasGranted: Boolean = false,
    val shouldShowRequestUi: Boolean = false,
    val isEnabled: Boolean = false
)


