package com.example.currencyconverter.transactions.presentation

import com.example.currencyconverter.transactions.domain.models.TransactionItem

data class TransactionsState(
    val transactions: List<TransactionItem> = emptyList()
)