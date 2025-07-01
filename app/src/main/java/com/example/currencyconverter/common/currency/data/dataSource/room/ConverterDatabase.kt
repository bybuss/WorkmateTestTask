package com.example.currencyconverter.common.currency.data.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.profile.data.data_source.room.dao.AccountDao
import com.example.currencyconverter.profile.data.data_source.room.dbo.AccountDbo
import com.example.currencyconverter.common.currency.data.dataSource.room.converter.Converters
import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import com.example.currencyconverter.transactions.data.data_source.room.dbo.TransactionDbo

@Database(entities = [AccountDbo::class, TransactionDbo::class], version = 1)
@TypeConverters(Converters::class)
abstract class ConverterDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}