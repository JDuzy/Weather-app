package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.dtos.CurrentWeatherResponseDto
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.TemperatureModel
import com.example.weatherapp.domain.model.WindModel

fun CurrentWeatherResponseDto.toCurrentWeatherModel() = CurrentWeatherModel(
    temperature = TemperatureModel(
        actualTemp = this.mainDto.temp,
        feelsLike = this.mainDto.feelsLike,
        maxTemp = this.mainDto.maxTemp,
        minTemp = this.mainDto.minTemp,
    ),
    description = this.weatherDto.description,
    iconId = this.weatherDto.iconId,
    wind = WindModel(
        speed = this.windDto.speed,
        degree = this.windDto.degree
    )
)