package com.example.weatherapp.data.remote.dtos

import com.squareup.moshi.Json

data class WindDto(
    @field:Json(name = "speed")
    val speed: Double? = null,
    @field:Json(name = "deg")
    val degree: Double? = null
)
