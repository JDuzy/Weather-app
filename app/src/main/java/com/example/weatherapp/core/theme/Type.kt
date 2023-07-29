package com.example.weatherapp.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

val poppins = FontFamily(
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_semi_bold, weight = FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = White
    ),
    titleLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = White
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = White
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = White
    ),
    labelMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = White
    )

)