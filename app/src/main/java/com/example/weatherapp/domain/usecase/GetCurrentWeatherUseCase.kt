package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val getGPSLocation: GetGPSLocationUseCase
) {

    suspend operator fun invoke(weatherMeasurableLocation: WeatherMeasurableLocationModel)  =
        weatherMeasurableLocation.location?.let { cityLocation ->
            currentWeatherRepository.getWeather(lat = cityLocation.lat, long = cityLocation.lon)
        } ?: run {
            val gpsLocation = getGPSLocation()
            gpsLocation?.let { gpsLoc ->
                currentWeatherRepository.getWeather(gpsLoc.lat, long = gpsLoc.lon)
            } ?: Resource.Error("Couldnt find location")
        }
}