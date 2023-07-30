package com.example.weatherapp.presentation

import com.example.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherapp.domain.usecase.GetGPSLocationUseCase
import com.example.weatherapp.domain.usecase.GetGpsMeasurableLocationModelUseCase
import com.example.weatherapp.domain.usecase.GetWeatherMeasurableLocationsToShowUseCase
import com.example.weatherapp.presentation.state.CurrentWeatherUiState
import com.example.weatherapp.util.FakeCurrentWeatherRepository
import com.example.weatherapp.util.FakeLocationTracker
import com.example.weatherapp.util.MainDispatcherRule
import com.example.weatherapp.util.mocks.CurrentWeatherMocks
import com.example.weatherapp.util.mocks.LocationMocks
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrentWeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        viewModel = CurrentWeatherViewModel(
            getCurrentWeatherUseCase = GetCurrentWeatherUseCase(
                currentWeatherRepository = FakeCurrentWeatherRepository(),
                getGPSLocation = GetGPSLocationUseCase(FakeLocationTracker())
            ),
            getWeatherMeasurableLocationsToShow = GetWeatherMeasurableLocationsToShowUseCase(
                GetGpsMeasurableLocationModelUseCase()
            ),
            getGpsMeasurableLocationModel = GetGpsMeasurableLocationModelUseCase()
        )
    }

    @Test
    fun setLocationPermissionSetsCorrectState() {
        viewModel.setLocationPermission(wasGranted = false, shouldRequest = false)
        var state = viewModel.locationPermissionState.value
        Assert.assertEquals(false, state.wasGranted)
        Assert.assertEquals(false, state.shouldShowRequestUi)
        viewModel.setLocationPermission(wasGranted = true, shouldRequest = true)
        state = viewModel.locationPermissionState.value
        Assert.assertEquals(true, state.wasGranted)
        Assert.assertEquals(true, state.shouldShowRequestUi)
        viewModel.setLocationPermission(wasGranted = false, shouldRequest = true)
        state = viewModel.locationPermissionState.value
        Assert.assertEquals(false, state.wasGranted)
        Assert.assertEquals(true, state.shouldShowRequestUi)
        viewModel.setLocationPermission(wasGranted = true, shouldRequest = false)
        state = viewModel.locationPermissionState.value
        Assert.assertEquals(true, state.wasGranted)
        Assert.assertEquals(false, state.shouldShowRequestUi)
    }

    @Test
    fun setLocationEnabledSetsCorrectState() {
        viewModel.setLocationEnabled(enabled = true)
        var state = viewModel.locationPermissionState.value
        Assert.assertEquals(true, state.isGPSEnabled)
        viewModel.setLocationEnabled(enabled = false)
        state = viewModel.locationPermissionState.value
        Assert.assertEquals(false, state.isGPSEnabled)
    }

    @Test
    fun getActualLocationCurrentWeather() {
        runTest {
            val firstState = viewModel.currentWeatherSate.value
            Assert.assertEquals(CurrentWeatherUiState.FirstTime, firstState)
            viewModel.getActualLocationCurrentWeather()
            advanceUntilIdle()
            val state = viewModel.currentWeatherSate.value as? CurrentWeatherUiState.Success
            Assert.assertEquals(CurrentWeatherMocks.gpsCurrentWeather, state?.currentWeatherModel)
        }
    }

    @Test
    fun onSelectLocationCorrectState() {
        runTest {
            viewModel.selectLocation(LocationMocks.genericMeasurableGpsLocation)
            advanceUntilIdle()
            val locationState = viewModel.locationSelectorUiState.value
            Assert.assertEquals(false, locationState.isExpanded)
            Assert.assertEquals(LocationMocks.genericMeasurableGpsLocation, locationState.selectedLocation)
            Assert.assertEquals(LocationMocks.genericMeasurableGpsLocation, locationState.locations.first())
            val state = viewModel.currentWeatherSate.value as? CurrentWeatherUiState.Success
            Assert.assertEquals(CurrentWeatherMocks.genericCurrentWeatherModel, state?.currentWeatherModel)
        }
    }

    @Test
    fun onExpandLocationSelectorCorrectState() {
        runTest {
            viewModel.expandLocationSelector()
            val state = viewModel.locationSelectorUiState.value
            Assert.assertEquals(true, state.isExpanded)
        }
    }
}