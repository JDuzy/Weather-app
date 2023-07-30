package com.example.weatherapp.core.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val lottieSize: Dp,
    val small: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val largeSpace: Dp,
    val mainTempTextSize: TextUnit
)

val smallDimensions = Dimensions(
    lottieSize = 180.dp,
    small = 4.dp,
    medium = 8.dp,
    mediumLarge = 12.dp,
    large = 16.dp,
    extraLarge = 20.dp,
    largeSpace = 64.dp,
    mainTempTextSize = 40.sp,
)

val compactDimensions = Dimensions(
    lottieSize = 220.dp,
    small = 4.dp,
    medium = 8.dp,
    mediumLarge = 12.dp,
    large = 16.dp,
    extraLarge = 20.dp,
    largeSpace = 86.dp,
    mainTempTextSize = 60.sp
)

val mediumDimensions = Dimensions(
    lottieSize = 260.dp,
    small = 8.dp,
    medium = 12.dp,
    mediumLarge = 16.dp,
    large = 20.dp,
    extraLarge = 24.dp,
    largeSpace = 100.dp,
    mainTempTextSize = 80.sp
)

val largeDimensions = Dimensions(
    lottieSize = 300.dp,
    small = 12.dp,
    medium = 16.dp,
    mediumLarge = 20.dp,
    large = 24.dp,
    extraLarge = 48.dp,
    largeSpace = 124.dp,
    mainTempTextSize = 90.sp
)