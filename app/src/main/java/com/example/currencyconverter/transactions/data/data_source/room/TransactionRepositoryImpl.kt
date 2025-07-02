package com.example.currencyconverter.transactions.data.data_source.room

import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import com.example.currencyconverter.transactions.data.mapper.toDbo
import com.example.currencyconverter.transactions.data.mapper.toDomain
import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.models.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllFlow(): Flow<List<Transaction>> =
        dao.getAllAsFlow().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(transaction: Transaction) {
        dao.insertAll(transaction.toDbo())
    }
}