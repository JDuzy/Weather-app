package com.example.weatherapp.presentation.view

import android.content.res.Configuration
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.R.string.should_request_location_permissions_description
import com.example.weatherapp.core.theme.AppTheme
import com.example.weatherapp.core.theme.Black
import com.example.weatherapp.core.theme.Orientation
import com.example.weatherapp.core.theme.WeatherAppTheme
import com.example.weatherapp.core.theme.White
import com.example.weatherapp.core.theme.poppins
import com.example.weatherapp.core.theme.rememberWindowSizeClass
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.LocationModel
import com.example.weatherapp.domain.model.TemperatureModel
import com.example.weatherapp.domain.model.WindModel
import com.example.weatherapp.presentation.CurrentWeatherViewModel
import com.example.weatherapp.presentation.state.CurrentWeatherUiState
import com.example.weatherapp.presentation.state.LocationPermissionUiState
import com.example.weatherapp.presentation.state.LocationSelectorUiState
import com.example.weatherapp.presentation.utils.toRawRes

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
    //if (AppTheme.orientation == Orientation.Portrait) {
        PortraitCurrentWeatherLayout(
            locationPermissionState = locationPermissionState,
            currentWeatherSate = currentWeatherSate,
            locationSelectorState = locationSelectorState,
            onRequestLocationPermission = onRequestLocationPermission,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation
        )
   /* } else {
        LandscapeCurrentWeatherLayout(
            locationPermissionState = locationPermissionState,
            currentWeatherSate = currentWeatherSate,
            locationSelectorState = locationSelectorState,
            onRequestLocationPermission = onRequestLocationPermission,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation
        )
    }*/
}

@Composable
private fun PortraitCurrentWeatherLayout(
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
            .padding(horizontal = AppTheme.dimens.mediumLarge)
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
                .padding(top = AppTheme.dimens.extraLarge),
            locationSelectorState = locationSelectorState,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation,
        )
    }
}

@Composable
private fun LandscapeCurrentWeatherLayout(
    locationPermissionState: LocationPermissionUiState,
    currentWeatherSate: CurrentWeatherUiState,
    locationSelectorState: LocationSelectorUiState,
    onRequestLocationPermission: () -> Unit = {},
    onExpandLocationSelector: () -> Unit = {},
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit = { _ -> }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
            .padding(horizontal = AppTheme.dimens.mediumLarge),

    ) {
        LocationSelector(
            modifier = Modifier
                .padding(top = AppTheme.dimens.extraLarge),
            locationSelectorState = locationSelectorState,
            onExpandLocationSelector = onExpandLocationSelector,
            onSelectLocation = onSelectLocation,
        )
        if (locationSelectorState.selectedLocation.isGPSLocation) {
            GPSLocationContent(
                locationPermissionState = locationPermissionState,
                currentWeatherSate = currentWeatherSate,
                onRequestLocationPermission = onRequestLocationPermission
            )
        } else {
            CityLocationContent(currentWeatherSate)
        }
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
        Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
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
        is CurrentWeatherUiState.Loading, CurrentWeatherUiState.FirstTime -> {
            LoadingCurrentWeatherContent()
        }

        is CurrentWeatherUiState.Error -> {
            ErrorFetchingWeatherContent()
        }

        is CurrentWeatherUiState.Success -> {
            CurrentWeatherSuccessContent(
                currentWeatherSate = currentWeatherSate
            )
        }
    }
}

@Composable
private fun LoadingCurrentWeatherContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
private fun ErrorFetchingWeatherContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorDescription(stringId = R.string.current_weather_location_error_description)
    }
}
@Composable
private fun CurrentWeatherSuccessContent(
    currentWeatherSate: CurrentWeatherUiState.Success,
) {
    if (AppTheme.orientation == Orientation.Portrait) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(AppTheme.dimens.largeSpace))
            WeatherLottie(
                currentWeatherSate.currentWeatherModel.iconId?.let {
                    it.toRawRes()
                } ?: "".toRawRes()
            )
            MainTemp(
                currentWeatherModel = currentWeatherSate.currentWeatherModel
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.extraLarge))
            WeatherInfo(
                currentWeatherModel = currentWeatherSate.currentWeatherModel
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MainTemp(
                currentWeatherModel = currentWeatherSate.currentWeatherModel
            )
            WeatherLottie(
                currentWeatherSate.currentWeatherModel.iconId?.let {
                    it.toRawRes()
                } ?: "".toRawRes()
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.extraLarge))
            WeatherInfo(
                currentWeatherModel = currentWeatherSate.currentWeatherModel
            )
        }
    }
}

@Composable
fun WeatherLottie(@RawRes rawId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawId))
    LottieAnimation(
        modifier = Modifier.size(AppTheme.dimens.lottieSize),
        composition =  composition,
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun ErrorDescription(@StringRes stringId: Int) {
    Text(
        modifier = Modifier.padding(horizontal = AppTheme.dimens.mediumLarge),
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
            text = stringResource(
                id = R.string.current_weather_actual_temperature_suffix,
                currentWeatherModel.temperature.actualTemp.toString()
            ),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = AppTheme.dimens.mainTempTextSize,
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_thermometer),
                contentDescription = null,
                tint = White
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.medium))
            Text(
                text = stringResource(
                    id = R.string.current_weather_min_max_temperature_text,
                    currentWeatherModel.temperature.minTemp.toString(),
                    currentWeatherModel.temperature.maxTemp.toString()
                ),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = null,
                tint = White
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.medium))
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
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
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
                actualTemp = 20,
                minTemp = 18,
                maxTemp = 22
            ),
            wind = WindModel(speed = 14.0, degree = 23.0),
            description = null,
            iconId = null
        )
    )
    val window = rememberWindowSizeClass()
    WeatherAppTheme(window) {
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