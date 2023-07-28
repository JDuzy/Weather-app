package com.example.weatherapp.domain.location

import com.example.weatherapp.domain.model.LocationModel

interface LocationTracker {

    suspend fun getCurrentLocation(): LocationModel?
}