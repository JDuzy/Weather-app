package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val getCurrentLocation: GetCurrentLocationUseCase,
) {

    suspend operator fun invoke(weatherMeasurableLocation: WeatherMeasurableLocationModel)  =
        weatherMeasurableLocation.location?.let { cityLocation ->
            currentWeatherRepository.getWeather(lat = cityLocation.lat, long = cityLocation.lon)
        } ?: run {
            val actualLocation = getCurrentLocation()
            actualLocation?.let { actual ->
                currentWeatherRepository.getWeather(actual.lat, long = actual.lon)
            } ?: Resource.Error("Couldnt find location")
        }

}