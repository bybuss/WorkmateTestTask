package com.example.currencyconverter.profile.data.data_source.room.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountDbo(
    @PrimaryKey
    @ColumnInfo(name = "currency_code")
    val code: String,
    val amount: Double
)