package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.dtos.CurrentWeatherResponseDto
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.TemperatureModel
import com.example.weatherapp.domain.model.WindModel
import kotlin.math.roundToInt

fun CurrentWeatherResponseDto.toCurrentWeatherModel() = CurrentWeatherModel(
    temperature = TemperatureModel(
        actualTemp = this.mainDto.temp?.roundToInt(),
        feelsLike = this.mainDto.feelsLike?.roundToInt(),
        maxTemp = this.mainDto.maxTemp?.roundToInt(),
        minTemp = this.mainDto.minTemp?.roundToInt(),
    ),
    description = this.weatherDtos.firstOrNull()?.description,
    iconId = this.weatherDtos.firstOrNull()?.iconId,
    wind = WindModel(
        speed = this.windDto.speed,
        degree = this.windDto.degree
    )
)