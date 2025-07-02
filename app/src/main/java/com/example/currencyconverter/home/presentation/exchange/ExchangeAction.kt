package com.example.currencyconverter.home.presentation.exchange

import com.example.currencyconverter.navigation.Screens

interface ExchangeAction {
    data class SetArgs(val args: Screens.Exchange) : ExchangeAction
    data object Confirm: ExchangeAction
    data object Back: ExchangeAction
    data class Exchange(
        val codeFrom: String,
        val codeTo: String,
        val amountFrom: Double,
        val amountTo: Double,
    ): ExchangeAction
}