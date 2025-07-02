package com.example.currencyconverter.profile.data.data_source.room

import com.example.currencyconverter.profile.data.data_source.room.dao.AccountDao
import com.example.currencyconverter.profile.data.mapper.toDomain
import com.example.currencyconverter.profile.data.mapper.toDbo
import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
) : AccountRepository {

    override fun getAllFlow(): Flow<List<Account>> = dao.getAllAsFlow().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun get(code: String): Account? =
        dao.getAll().firstOrNull { it.code == code }?.toDomain()

    override suspend fun updateAmount(code: String, amount: Double) {
        dao.insertAll(com.example.currencyconverter.profile.data.data_source.room.dbo.AccountDbo(code, amount))
    }

    override suspend fun insert(account: Account) {
        dao.insertAll(account.toDbo())
    }
}