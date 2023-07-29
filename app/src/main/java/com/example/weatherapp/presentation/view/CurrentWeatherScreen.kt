package com.example.weatherapp.presentation.view

import android.content.res.Configuration
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.R.string.should_request_location_permissions_description
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
    viewModel: CurrentWeatherViewModel,
    onRequestLocationPermission: () -> Unit
) {
    val locationPermissionState by viewModel.locationPermissionState.collectAsState()
    val currentWeatherSate by viewModel.currentWeatherSate.collectAsState()
    val locationSelectorState by viewModel.locationSelectorUiState.collectAsState()

    CurrentWeatherScreen(
        locationPermissionState = locationPermissionState,
        currentWeatherSate = currentWeatherSate,
        locationSelectorState = locationSelectorState,
        onRequestLocationPermission = onRequestLocationPermission,
        onExpandLocationSelector = viewModel::expandLocationSelector,
        onSelectLocation = viewModel::selectLocation
    )
}

@Composable
private fun CurrentWeatherScreen(
    locationPermissionState: LocationPermissionUiState,
    currentWeatherSate: CurrentWeatherUiState,
    locationSelectorState: LocationSelectorUiState,
    onRequestLocationPermission: () -> Unit = {},
    onExpandLocationSelector: () -> Unit = {},
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit = { _ -> }
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Black)
            .padding(horizontal = 16.dp)
    ) {

        if (locationSelectorState.selectedLocation.isGPSLocation) {
            GPSLocationContent(
                locationPermissionState = locationPermissionState,
                currentWeatherSate = currentWeatherSate,
                onRequestLocationPermission = onRequestLocationPermission
            )
        } else {
            CityLocationContent(currentWeatherSate)
        }
        LocationSelector(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            locationSelectorState = locationSelectorState,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation,
        )
    }
}

@Composable
private fun GPSLocationContent(
    locationPermissionState: LocationPermissionUiState,
    currentWeatherSate: CurrentWeatherUiState,
    onRequestLocationPermission: () -> Unit,
) {
    if (locationPermissionState.shouldShowRequestUi) {
        RequestForPermissionsContent(onRequestLocationPermission = onRequestLocationPermission)
    } else if (!locationPermissionState.wasGranted) {
        PermissionNotGrantedContent()
    } else if (!locationPermissionState.isGPSEnabled) {
        AskForGPSContent()
    } else {
        CurrentWeatherContent(currentWeatherSate)
    }
}

@Composable
private fun RequestForPermissionsContent(onRequestLocationPermission: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorDescription(stringId = should_request_location_permissions_description)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRequestLocationPermission
        ) {
            Text(text = stringResource(id = R.string.require_permissions_btn))
        }
    }
}

@Composable
private fun PermissionNotGrantedContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorDescription(stringId = R.string.location_permissions_denied_description)
    }

}

@Composable
private fun AskForGPSContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorDescription(stringId = R.string.gps_not_granted_state_description)
    }
}

@Composable
private fun CityLocationContent(currentWeatherSate: CurrentWeatherUiState) {
    CurrentWeatherContent(currentWeatherSate)
}

@Composable
private fun CurrentWeatherContent(currentWeatherSate: CurrentWeatherUiState) {
    when (currentWeatherSate) {
        is CurrentWeatherUiState.Loading -> {
            CircularProgressIndicator()
        }

        is CurrentWeatherUiState.Error -> {
            Text("Error")
        }

        is CurrentWeatherUiState.Success -> {
            CurrentWeatherSuccessContent(
                currentWeatherSate = currentWeatherSate
            )
        }
    }
}

@Composable
private fun CurrentWeatherSuccessContent(
    currentWeatherSate: CurrentWeatherUiState.Success,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        WeatherLottie(R.raw.broken_clouds)
        MainTemp(
            currentWeatherModel = currentWeatherSate.currentWeatherModel
        )
        Spacer(modifier = Modifier.height(24.dp))
        WeatherInfo(
            currentWeatherModel = currentWeatherSate.currentWeatherModel
        )
    }

}

@Composable
fun WeatherLottie(@RawRes rawId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawId))
    LottieAnimation(
        modifier = Modifier.size(260.dp),
        composition =  composition,
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun ErrorDescription(@StringRes stringId: Int) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(id = stringId),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun MainTemp(
    currentWeatherModel: CurrentWeatherModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = currentWeatherModel.temperature.actualTemp.toString(),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 80.sp,
            color = White
        )
        Text(
            text = currentWeatherModel.description ?: "",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun WeatherInfo(currentWeatherModel: CurrentWeatherModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(
                id = R.string.current_weather_min_max_temperature_text,
                currentWeatherModel.temperature.minTemp.toString(),
                currentWeatherModel.temperature.maxTemp.toString()
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.current_weather_wind_suffix,
                currentWeatherModel.wind.speed.toString()
            ),
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
        location = null,
        isGPSLocation = true
    )
    val currentWeatherSateSuccess = CurrentWeatherUiState.Success(
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
    )

    WeatherAppTheme {
        CurrentWeatherScreen(
            locationPermissionState = LocationPermissionUiState(
                wasGranted = true,
                shouldShowRequestUi = false,
                isGPSEnabled = true
            ),
            currentWeatherSate = currentWeatherSateSuccess,
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