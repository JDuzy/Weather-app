package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.LocationModel
import com.example.weatherapp.util.FakeLocationTracker
import com.example.weatherapp.util.mocks.LocationMocks
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetGpsLocationUseCaseTest {

    private val useCase = GetGPSLocationUseCase(FakeLocationTracker())
    private var locationModel: LocationModel? = null

    @Before
    fun setUp() {
        runTest {
            locationModel = useCase()
        }
    }

    @Test
    fun useCaseReturnsGpsLocationModel() {
        Assert.assertTrue(locationModel != null)
        Assert.assertEquals(LocationMocks.gpsLocation.lat, locationModel?.lat)
        Assert.assertEquals(LocationMocks.gpsLocation.lon, locationModel?.lon)
    }
}