package com.example.currencyconverter.transactions.domain.dataSource.room

import com.example.currencyconverter.transactions.domain.models.Transaction

interface TransactionRepository {
    suspend fun getAll(): List<Transaction>
    suspend fun insert(transaction: Transaction)
}