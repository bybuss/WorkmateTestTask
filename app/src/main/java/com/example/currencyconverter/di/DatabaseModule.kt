package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.common.currency.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.profile.data.data_source.room.dao.AccountDao
import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ConverterDatabase =
        Room.databaseBuilder(context, ConverterDatabase::class.java, "converter.db")
            .createFromAsset("databases/converter.db")
            .build()

    @Provides
    fun provideAccountDao(db: ConverterDatabase): AccountDao = db.accountDao()

    @Provides
    fun provideTransactionDao(db: ConverterDatabase): TransactionDao = db.transactionDao()
}