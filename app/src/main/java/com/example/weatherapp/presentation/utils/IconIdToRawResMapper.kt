package com.example.weatherapp.presentation.utils

import com.example.weatherapp.R.raw.broken_clouds
import com.example.weatherapp.R.raw.clear_sky_day
import com.example.weatherapp.R.raw.clear_sky_night
import com.example.weatherapp.R.raw.few_clouds_day
import com.example.weatherapp.R.raw.few_clouds_night
import com.example.weatherapp.R.raw.mist
import com.example.weatherapp.R.raw.rain_day
import com.example.weatherapp.R.raw.rain_night
import com.example.weatherapp.R.raw.scattered_clouds
import com.example.weatherapp.R.raw.shower_rain
import com.example.weatherapp.R.raw.snow
import com.example.weatherapp.R.raw.thunderstorm

fun String?.toRawRes() =
    when(this) {
       "01d" -> clear_sky_day
       "01n" -> clear_sky_night
       "02d" -> few_clouds_day
       "02n" -> few_clouds_night
       "03d" -> scattered_clouds
       "03n" -> scattered_clouds
       "04d" -> broken_clouds
       "04n" -> broken_clouds
       "09d" -> shower_rain
       "09n" -> shower_rain
       "10d" -> rain_day
       "10n" -> rain_night
       "11d" -> thunderstorm
       "11n" -> thunderstorm
       "13d" -> snow
       "13n" -> snow
       "50d" -> mist
       "50n" -> mist
       else -> clear_sky_day
    }