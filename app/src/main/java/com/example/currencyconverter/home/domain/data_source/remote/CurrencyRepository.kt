package com.example.currencyconverter.home.domain.data_source.remote

import com.example.currencyconverter.common.utils.Result
import com.example.currencyconverter.home.domain.models.Rate

interface CurrencyRepository {
    suspend fun loadRates(base: String, amount: Double): Result<List<Rate>>
    fun getCurrencyName(code: String): String
    fun getFlagResId(code: String): Int
}