package com.example.currencyconverter.home.presentation.currencies

import com.example.currencyconverter.common.utils.UiState
import com.example.currencyconverter.home.domain.models.CurrencyItem

data class CurrencyListState(
    val baseCurrency: String = "EUR",
    val amountInput: String = "1",
    val isEditing: Boolean = false,
    val items: UiState<List<CurrencyItem>> = UiState.Loading
)
