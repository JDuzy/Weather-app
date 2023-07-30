package com.example.weatherapp.util

import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.model.LocationModel
import com.example.weatherapp.util.mocks.LocationMocks

class FakeLocationTracker: LocationTracker {
    override suspend fun getCurrentLocation(): LocationModel? =
        LocationMocks.gpsLocation
}