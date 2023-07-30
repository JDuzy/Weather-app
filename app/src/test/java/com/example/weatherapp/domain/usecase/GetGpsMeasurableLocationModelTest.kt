package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetGpsMeasurableLocationModelTest {

    private val useCase = GetGpsMeasurableLocationModelUseCase()
    private lateinit var model: WeatherMeasurableLocationModel

    @Before
    fun setUp() {
        model = useCase()
    }

    @Test
    fun weatherMeasurableLocationIsGpsLocation() {
        Assert.assertEquals(true, model.isGPSLocation)
    }

    @Test
    fun gpsWeatherMeasurableLocationHasCorrectName() {
        Assert.assertEquals("Current location", model.name)
    }
}