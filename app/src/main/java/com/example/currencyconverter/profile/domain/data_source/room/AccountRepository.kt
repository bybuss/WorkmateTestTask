package com.example.currencyconverter.profile.domain.data_source.room

import com.example.currencyconverter.profile.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllFlow(): Flow<List<Account>>
    suspend fun get(code: String): Account?
    suspend fun updateAmount(code: String, amount: Double)
    suspend fun insert(account: Account)
}