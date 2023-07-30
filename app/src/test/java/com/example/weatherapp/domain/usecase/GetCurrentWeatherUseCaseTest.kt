package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.Resource
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.util.FakeCurrentWeatherRepository
import com.example.weatherapp.util.FakeLocationTracker
import com.example.weatherapp.util.mocks.CurrentWeatherMocks
import com.example.weatherapp.util.mocks.LocationMocks
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetCurrentWeatherUseCaseTest {

    private val useCase = GetCurrentWeatherUseCase(
        currentWeatherRepository = FakeCurrentWeatherRepository(),
        getGPSLocation = GetGPSLocationUseCase(FakeLocationTracker())
    )

    private lateinit var response: Resource<CurrentWeatherModel>

    @Test
    fun getGpsCurrentWeatherIsSuccess() {
        runTest {
            response = useCase(LocationMocks.weatherMeasurableGpsLocation)
            Assert.assertTrue(response is Resource.Success)
            Assert.assertTrue(response.data != null)
            Assert.assertEquals(response.data, CurrentWeatherMocks.gpsCurrentWeather)
        }
    }

    @Test
    fun getGenericCityCurrentWeatherIsSuccess() {
        runTest {
            response = useCase(LocationMocks.genericMeasurableGpsLocation)
            Assert.assertTrue(response is Resource.Success)
            Assert.assertTrue(response.data != null)
            Assert.assertEquals(response.data, CurrentWeatherMocks.genericCurrentWeatherModel)
        }
    }

    @Test
    fun getInvalidLocationCurrentWeatherIsError() {
        runTest {
            response = useCase(LocationMocks.invalidParametersWeatherMeasurableLocation)
            Assert.assertTrue(response is Resource.Error)
            Assert.assertTrue(response.message != null)
            Assert.assertEquals(response.message, CurrentWeatherMocks.errorMessage)
        }
    }
}