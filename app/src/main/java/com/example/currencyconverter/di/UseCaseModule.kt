package com.example.currencyconverter.di

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.home.domain.use_cases.GetCurrencyNameUseCase
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.home.domain.use_cases.LoadRatesUseCase
import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.use_cases.GetAccountUseCase
import com.example.currencyconverter.profile.domain.use_cases.GetAccountsUseCase
import com.example.currencyconverter.profile.domain.use_cases.InsertAccountUseCase
import com.example.currencyconverter.profile.domain.use_cases.UpdateAccountAmountUseCase
import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.use_cases.GetTransactionsUseCase
import com.example.currencyconverter.transactions.domain.use_cases.InsertTransactionUseCase
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

    @Provides
    @Singleton
    fun provideGetAccountsUseCase(repository: AccountRepository): GetAccountsUseCase =
        GetAccountsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAccountUseCase(repository: AccountRepository): GetAccountUseCase =
        GetAccountUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateAccountAmountUseCase(repository: AccountRepository): UpdateAccountAmountUseCase =
        UpdateAccountAmountUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertAccountUseCase(repository: AccountRepository): InsertAccountUseCase =
        InsertAccountUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactionsUseCase =
        GetTransactionsUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransactionUseCase =
        InsertTransactionUseCase(repository)
}