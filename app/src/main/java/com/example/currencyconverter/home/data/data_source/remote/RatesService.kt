package com.example.currencyconverter.home.data.data_source.remote

import com.example.currencyconverter.home.data.data_source.remote.dto.RateDto

interface RatesService {
    suspend fun getRates(
        baseCurrencyCode: String,
        amount: Double
    ): List<RateDto>
}