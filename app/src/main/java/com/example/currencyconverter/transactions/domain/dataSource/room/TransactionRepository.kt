package com.example.currencyconverter.transactions.domain.dataSource.room

import com.example.currencyconverter.transactions.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllFlow(): Flow<List<Transaction>>
    suspend fun insert(transaction: Transaction)
}