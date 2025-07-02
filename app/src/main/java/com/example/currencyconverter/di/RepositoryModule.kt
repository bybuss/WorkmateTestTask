package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.home.data.data_source.remote.CurrencyRepositoryImpl
import com.example.currencyconverter.home.data.data_source.remote.RatesService
import com.example.currencyconverter.home.data.data_source.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.profile.data.data_source.room.AccountRepositoryImpl
import com.example.currencyconverter.profile.data.data_source.room.dao.AccountDao
import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.transactions.data.data_source.room.TransactionRepositoryImpl
import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
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

    @Provides
    @Singleton
    fun provideAccountRepository(dao: AccountDao): AccountRepository =
        AccountRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideTransactionRepository(dao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(dao)
}