package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import javax.inject.Inject

class GetCurrencySymbolUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(code: String): String = repository.getCurrencySymbol(code)
}