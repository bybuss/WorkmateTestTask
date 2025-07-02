package com.example.currencyconverter.transactions.presentation

import com.example.currencyconverter.transactions.data.data_source.room.dbo.TransactionDbo

data class TransactionsState(
    val transactions: List<TransactionDbo> = emptyList()
)