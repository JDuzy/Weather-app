package com.example.weatherapp.data.remote.dtos

import com.squareup.moshi.Json

data class MainDto(
    @field:Json(name = "temp")
    val temp: Double? = null,
    @field:Json(name = "feels_like")
    val feelsLike: Double? = null,
    @field:Json(name = "temp_min")
    val minTemp: Double? = null,
    @field:Json(name = "temp_max")
    val maxTemp: Double? = null
)
