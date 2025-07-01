package com.example.currencyconverter.home.data.data_source.remote

import android.content.Context
import com.example.currencyconverter.R
import com.example.currencyconverter.common.utils.Result
import com.example.currencyconverter.common.utils.safeApiCall
import com.example.currencyconverter.home.data.data_source.remote.dto.RateDto
import com.example.currencyconverter.home.data.mapper.toDomain
import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.home.domain.models.Rate
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val service: RatesService,
    private val context: Context
) : CurrencyRepository {

    private val resources = context.resources
    private val packageName = context.packageName

    override suspend fun loadRates(base: String, amount: Double): Result<List<Rate>> {
        return safeApiCall <List<RateDto>, List<Rate>>(
            apiCall = { service.getRates(base, amount) },
            successHandler = { it.toDomain() },
            context = context
        )
    }

    override fun getCurrencyName(code: String): String {
        val id = resources.getIdentifier(
            "currency_${code.lowercase()}",
            "string",
            packageName
        )
        return if (id != 0) resources.getString(id) else code
    }

    override fun getFlagResId(code: String): Int {
        val countryCode = when (code) {
            "EUR" -> "eu"
            "GBP" -> "gb"
            "USD" -> "us"
            else -> code.substring(0, 2).lowercase()
        }
        val id = resources.getIdentifier(
            "flag_$countryCode",
            "drawable",
            packageName
        )
        return if (id != 0) id else R.drawable.flag_unknown
    }
}