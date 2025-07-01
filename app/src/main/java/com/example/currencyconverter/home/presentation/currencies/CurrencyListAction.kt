package com.example.currencyconverter.home.presentation.currencies

interface CurrencyListAction {
    data class SelectCurrency(val code: String): CurrencyListAction
    data class ChangeAmount(val value: String): CurrencyListAction
    data object StartEdit: CurrencyListAction
    data object ClearAmount: CurrencyListAction
    data class NavigateToExchange(val codeTo: String): CurrencyListAction
}