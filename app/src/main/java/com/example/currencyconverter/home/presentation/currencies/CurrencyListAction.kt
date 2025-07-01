package com.example.currencyconverter.home.presentation.currencies

interface CurrencyListAction {
    data class SelectCurrency(val code: String): CurrencyListAction
    data class ChangeAmount(val value: String): CurrencyListAction
    object StartEdit: CurrencyListAction
    object ClearAmount: CurrencyListAction
}