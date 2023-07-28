package com.example.weatherapp.data.di

import com.example.weatherapp.data.repository.CurrentWeatherRepositoryImpl
import com.example.weatherapp.domain.repository.CurrentWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(currentWeatherRepositoryImpl: CurrentWeatherRepositoryImpl): CurrentWeatherRepository
}