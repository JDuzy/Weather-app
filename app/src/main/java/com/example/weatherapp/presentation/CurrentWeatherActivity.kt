package com.example.weatherapp.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.weatherapp.core.theme.WeatherAppTheme
import com.example.weatherapp.presentation.view.CurrentWeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherActivity : ComponentActivity() {

    private val viewModel: CurrentWeatherViewModel by viewModels()

    private val locationRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.setLocationEnabled(true)
            viewModel.getGpsLocationCurrentWeather()
        } else {
            viewModel.setLocationEnabled(false)
        }
    }

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            viewModel.setLocationPermission(
                wasGranted = false,
                shouldRequest = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        } else {
            viewModel.setLocationPermission(wasGranted = isGranted, shouldRequest = false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForPermissions()

        createLocationRequest(
            activity = this,
            locationRequestLauncher = locationRequestLauncher,
            onLocationRequestSuccessful = {
                viewModel.setLocationEnabled(true)
                viewModel.getGpsLocationCurrentWeather()
            }
        )
        setContent {
            WeatherAppTheme {
                CurrentWeatherScreen(viewModel = viewModel, onRequestLocationPermission = ::requestLocationPermission)
            }
        }
    }

    private fun checkForPermissions() {
        when (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PackageManager.PERMISSION_GRANTED -> {
                viewModel.setLocationPermission(wasGranted = true, shouldRequest = false)
            }

            PackageManager.PERMISSION_DENIED -> {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    viewModel.setLocationPermission(
                        wasGranted = false,
                        shouldRequest = true
                    )
                } else {
                    viewModel.setLocationPermission(
                        wasGranted = false,
                        shouldRequest = false
                    )
                    requestLocationPermission()
                }

            }
        }
    }

    private fun requestLocationPermission() {
        permissionRequestLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}
