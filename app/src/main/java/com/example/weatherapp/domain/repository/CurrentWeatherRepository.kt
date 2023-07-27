package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.CurrentWeatherModel

interface CurrentWeatherRepository {

    suspend fun getWeather(lat: Double, long: Double): Resource<CurrentWeatherModel>
}