package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository
) {

    suspend operator fun invoke(lat: Double, lon: Double) =
        currentWeatherRepository.getWeather(lat = lat, long = lon)
}