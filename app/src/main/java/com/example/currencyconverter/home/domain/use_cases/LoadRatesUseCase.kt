package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.home.domain.models.Rate
import com.example.currencyconverter.common.utils.Result
import javax.inject.Inject

class LoadRatesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke(base: String, amount: Double): Result<List<Rate>> {
        return repository.loadRates(base, amount)
    }
}