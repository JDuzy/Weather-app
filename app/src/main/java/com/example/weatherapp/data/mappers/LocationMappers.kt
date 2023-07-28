package com.example.weatherapp.data.mappers

import android.location.Location
import com.example.weatherapp.domain.model.LocationModel

fun Location.toLocationModel() = LocationModel(
    lat = this.latitude,
    lon = this.longitude
)