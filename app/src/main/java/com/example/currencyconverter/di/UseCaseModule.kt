package com.example.currencyconverter.di

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.home.domain.use_cases.GetCurrencyNameUseCase
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.home.domain.use_cases.LoadRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoadRatesUseCase(repository: CurrencyRepository): LoadRatesUseCase =
        LoadRatesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCurrencyNameUseCase(repository: CurrencyRepository): GetCurrencyNameUseCase =
        GetCurrencyNameUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFlagResIdUseCase(repository: CurrencyRepository): GetFlagResIdUseCase =
        GetFlagResIdUseCase(repository)
}