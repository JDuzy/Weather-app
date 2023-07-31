package com.example.weatherapp.util

import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import com.example.weatherapp.util.mocks.CurrentWeatherMocks
import com.example.weatherapp.util.mocks.LocationMocks

class FakeCurrentWeatherRepository: CurrentWeatherRepository {

    override suspend fun getWeather(lat: Double, long: Double): Resource<CurrentWeatherModel>  =
        if (lat == LocationMocks.gpsLocation.lat && long == LocationMocks.gpsLocation.lon) {
            Resource.Success(
                CurrentWeatherMocks.gpsCurrentWeather
            )
        } else if (lat < 0 || long < 0){
            Resource.Error(message = CurrentWeatherMocks.errorMessage)
        } else {
            Resource.Success(CurrentWeatherMocks.genericCurrentWeatherModel)
    }
}