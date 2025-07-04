package com.example.currencyconverter.transactions.domain.use_cases

import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.models.Transaction
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) = repository.insert(transaction)
}