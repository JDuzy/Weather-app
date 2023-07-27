package com.example.weatherapp.data.remote.dtos

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "main")
    val main: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "icon")
    val iconId: String? = null
)
