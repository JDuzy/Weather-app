package com.example.weatherapp.data.remote

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dtos.CurrentWeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val METRIC = "metric"

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("units") units: String = METRIC,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): CurrentWeatherResponseDto
}