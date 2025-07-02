package com.example.currencyconverter.transactions.domain.models

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val from: String,
    val to: String,
    val fromAmount: Double,
    val toAmount: Double,
    val dateTime: LocalDateTime,
)
