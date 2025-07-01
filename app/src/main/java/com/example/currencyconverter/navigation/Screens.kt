package com.example.currencyconverter.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Currencies: Screens

    @Serializable
    data class Exchange(val codeFrom: String): Screens

    @Serializable
    data object TransactionHistory: Screens

    @Serializable
    data object Profile: Screens
}