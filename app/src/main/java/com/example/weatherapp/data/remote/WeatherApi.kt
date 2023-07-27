package com.example.weatherapp.data.remote

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dtos.CurrentWeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("https://api.openweathermap.org/data/2.5/weather?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): CurrentWeatherResponseDto
}