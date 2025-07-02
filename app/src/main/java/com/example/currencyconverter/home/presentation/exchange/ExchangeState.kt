package com.example.currencyconverter.home.presentation.exchange

import com.example.currencyconverter.navigation.Screens

data class ExchangeState(
    val args: Screens.Exchange? = null,
    val errorMessage: String? = null,
)
