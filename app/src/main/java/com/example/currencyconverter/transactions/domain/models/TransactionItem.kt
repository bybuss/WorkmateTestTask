package com.example.currencyconverter.transactions.domain.models

data class TransactionItem(
    val id: Int,
    val flagFrom: Int?,
    val flagTo: Int?,
    val pairText: String,
    val amountText: String,
)
