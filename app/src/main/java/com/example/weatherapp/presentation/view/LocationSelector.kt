package com.example.weatherapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.theme.AppTheme
import com.example.weatherapp.core.theme.Grey
import com.example.weatherapp.domain.model.WeatherMeasurableLocationModel
import com.example.weatherapp.presentation.state.LocationSelectorUiState

@Composable
internal fun LocationSelector(
    modifier: Modifier = Modifier,
    locationSelectorState: LocationSelectorUiState,
    onExpandLocationSelector: () -> Unit,
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit = { _ -> }
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .clickable(onClick = onExpandLocationSelector),
        color = Grey.copy(0.9f),
        contentColor = Color.White,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp
    ) {
        if (locationSelectorState.isExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationRow(
                    location = locationSelectorState.locations.first(),
                    onSelectLocation = onSelectLocation
                )
                for (location in locationSelectorState.locations.subList(
                    fromIndex = 1,
                    toIndex = locationSelectorState.locations.lastIndex + 1
                )) {
                    Divider(
                        Modifier
                            .height(1.dp)
                            .padding(horizontal = AppTheme.dimens.small),
                        color = Color.Black
                    )
                    LocationRow(location = location, onSelectLocation = onSelectLocation)
                }
            }

        } else {
            ContractedLocationSelectorContent(
                location = locationSelectorState.selectedLocation,
                onExpandLocationSelector = onExpandLocationSelector
            )
        }
    }

}

@Composable
private fun LocationRow(
    location: WeatherMeasurableLocationModel,
    onSelectLocation: (WeatherMeasurableLocationModel) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onSelectLocation(location) }
            .padding(
                horizontal = AppTheme.dimens.mediumLarge, vertical = AppTheme.dimens.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
internal fun ContractedLocationSelectorContent(
    location: WeatherMeasurableLocationModel,
    onExpandLocationSelector: () -> Unit
) {
    Row(
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.mediumLarge,
            vertical = AppTheme.dimens.mediumLarge
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Spacer(modifier = Modifier.width(AppTheme.dimens.mediumLarge))
        Text(
            text = location.name,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(AppTheme.dimens.large),
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Color.White
        )
    }
}