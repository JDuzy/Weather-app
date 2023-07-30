package com.example.weatherapp.util.mocks

import com.example.weatherapp.domain.model.LocationModel
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel

object LocationMocks {

    val gpsLocation = LocationModel(0.0,0.0)
    val genericLocation = LocationModel(10.0,10.0)
    val invalidLocation = LocationModel(-1.0,-1.0)

    val weatherMeasurableGpsLocation = WeatherMeasurableLocationModel(
        name = "Actual location",
        location = gpsLocation,
        isGPSLocation = true
    )
    val genericMeasurableGpsLocation =  WeatherMeasurableLocationModel(
        name = "Generic",
        location = genericLocation,
        isGPSLocation = false
    )

    val invalidParametersWeatherMeasurableLocation =  WeatherMeasurableLocationModel(
        name = "Invalid coords",
        location = invalidLocation,
        isGPSLocation = false
    )
}