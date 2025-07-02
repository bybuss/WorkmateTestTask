package com.example.currencyconverter.home.domain.models

import androidx.annotation.DrawableRes

data class CurrencyItem(
    val code: String,
    val name: String,
    @DrawableRes val flagRes: Int,
    val rate: Double,
    val symbol: String
)