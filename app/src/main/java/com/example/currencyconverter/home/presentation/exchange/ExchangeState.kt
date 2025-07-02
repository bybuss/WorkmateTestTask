package com.example.currencyconverter.home.presentation.exchange

import com.example.currencyconverter.navigation.Screens

data class ExchangeState(
    val args: Screens.Exchange? = null,
    val title: String = "",
    val rateText: String = "",
    val fromFlag: Int? = null,
    val toFlag: Int? = null,
    val fromAmountText: String = "",
    val toAmountText: String = "",
    val buttonText: String = "",
    val errorMessage: String? = null
)
