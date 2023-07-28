package com.example.weatherapp.data.di

import android.app.Application
import com.example.weatherapp.data.remote.WeatherApi
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/weather")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    fun provideFuseLocationProviderClient(app: Application) =
        LocationServices.getFusedLocationProviderClient(app)
}