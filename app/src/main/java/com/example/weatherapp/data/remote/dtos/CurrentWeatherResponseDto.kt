package com.example.weatherapp.data.remote.dtos

import com.squareup.moshi.Json

data class CurrentWeatherResponseDto(
    @field:Json(name = "weather")
    val weatherDtos: List<WeatherDto>,
    @field:Json(name = "main")
    val mainDto: MainDto,
    @field:Json(name = "wind")
    val windDto: WindDto
)
