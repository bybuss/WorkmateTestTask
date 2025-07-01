package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.common.currency.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.home.data.data_source.remote.CurrencyRepositoryImpl
import com.example.currencyconverter.home.data.data_source.remote.RatesService
import com.example.currencyconverter.home.data.data_source.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.home.domain.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRatesService(): RatesService = RemoteRatesServiceImpl()

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        service: RatesService,
        @ApplicationContext context: Context
    ): CurrencyRepository = CurrencyRepositoryImpl(service, context)
}