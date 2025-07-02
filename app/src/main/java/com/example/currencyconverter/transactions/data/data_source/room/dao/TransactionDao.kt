package com.example.currencyconverter.transactions.data.data_source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconverter.transactions.data.data_source.room.dbo.TransactionDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertAll(vararg transactions: TransactionDbo)

    @Query("SELECT * FROM transactions")
    fun getAllAsFlow(): Flow<List<TransactionDbo>>
}