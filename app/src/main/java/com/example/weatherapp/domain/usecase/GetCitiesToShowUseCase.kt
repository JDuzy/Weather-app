package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.CityModel
import com.example.weatherapp.domain.model.LocationModel
import javax.inject.Inject

class GetCitiesToShowUseCase @Inject constructor() {

    operator fun invoke() =
        listOf(
            CityModel(
                "Montevideo",
                location = LocationModel(22.2, 22.2)
            ),
            CityModel(
                "Londres",
                location = LocationModel(22.2, 22.2)
            ),
            CityModel(
                "San Pablo",
                location = LocationModel(22.2, 22.2)
            ),
            CityModel(
                "Buenos aires",
                location = LocationModel(22.2, 22.2)
            )
        )
}