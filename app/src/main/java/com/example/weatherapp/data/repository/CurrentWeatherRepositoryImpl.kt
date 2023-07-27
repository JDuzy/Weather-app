package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mappers.toCurrentWeatherModel
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : CurrentWeatherRepository {
    override suspend fun getWeather(lat: Double, long: Double): Resource<CurrentWeatherModel> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Resource.Success(
                    data = weatherApi.getWeather(lat, long).toCurrentWeatherModel()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.message ?: "An unknown error has ocurred")
            }
        }
}