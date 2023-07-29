package com.example.weatherapp.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.theme.Black
import com.example.weatherapp.core.theme.WeatherAppTheme
import com.example.weatherapp.core.theme.White
import com.example.weatherapp.core.theme.poppins
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.LocationModel
import com.example.weatherapp.domain.model.TemperatureModel
import com.example.weatherapp.domain.model.WindModel
import com.example.weatherapp.presentation.CurrentWeatherViewModel
import com.example.weatherapp.presentation.state.CurrentWeatherUiState
import com.example.weatherapp.presentation.state.LocationPermissionUiState
import com.example.weatherapp.presentation.state.LocationSelectorUiState

@Composable
internal fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel
) {
    val locationPermissionState by viewModel.locationPermissionState.collectAsState()
    val currentWeatherSate by viewModel.currentWeatherSate.collectAsState()
    val locationSelectorState by viewModel.locationSelectorUiState.collectAsState()

    CurrentWeatherScreen(
        locationPermissionState = locationPermissionState,
        currentWeatherSate = currentWeatherSate,
        locationSelectorState = locationSelectorState,
        onExpandLocationSelector = viewModel::expandLocationSelector,
        onSelectLocation = viewModel::selectLocation
    )
}

@Composable
private fun CurrentWeatherScreen(
    locationPermissionState: LocationPermissionUiState,
    currentWeatherSate: CurrentWeatherUiState,
    locationSelectorState: LocationSelectorUiState,
    onExpandLocationSelector: () -> Unit = {},
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit = { _ -> }
) {
    if (currentWeatherSate is CurrentWeatherUiState.Success) {
        SuccessContent(
            currentWeatherSate = currentWeatherSate,
            locationSelectorState = locationSelectorState,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation
        )
    }
}

@Composable
private fun SuccessContent(
    currentWeatherSate: CurrentWeatherUiState.Success,
    locationSelectorState: LocationSelectorUiState,
    onExpandLocationSelector: () -> Unit,
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit = { _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        LocationSelector(
            locationSelectorState = locationSelectorState,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth(0.5f))
        Spacer(modifier = Modifier.height(24.dp))
        MainTemp()
        Spacer(modifier = Modifier.height(24.dp))
        WeatherInfo()
    }
}

@Composable
private fun MainTemp() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "22°",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 80.sp,
            color = White
        )
        Text(
            text = "Nublado",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun WeatherInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "18° min / 24° max",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Wind 24km/h",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewScreen() {
    val selectedLocation = WeatherMeasurableLocationModel(
        name = "Ubicación actual",
        location = null
    )
    WeatherAppTheme {
        CurrentWeatherScreen(
            locationPermissionState = LocationPermissionUiState(
                wasGranted = true,
                shouldShowRequestUi = false,
                isEnabled = true
            ),
            currentWeatherSate = CurrentWeatherUiState.Success(
                currentWeatherModel = CurrentWeatherModel(
                    temperature = TemperatureModel(
                        actualTemp = 20.0,
                        minTemp = 18.0,
                        maxTemp = 22.0
                    ),
                    wind = WindModel(speed = 14.0, degree = 23.0),
                    description = null,
                    iconId = null
                )
            ),
            locationSelectorState = LocationSelectorUiState(
                isExpanded = false,
                selectedLocation = selectedLocation,
                locations = listOf(
                    selectedLocation,
                    WeatherMeasurableLocationModel(
                        name = "Buenos Aires",
                        location = LocationModel(0.0, 0.0)
                    ),
                    WeatherMeasurableLocationModel(
                        name = "Montevideo",
                        location = LocationModel(0.0, 0.0)
                    ),
                    WeatherMeasurableLocationModel(
                        name = "San pablo",
                        location = LocationModel(0.0, 0.0)
                    )
                )
            )
        )
    }
}