package com.example.currencyconverter.transactions.data.data_source.room

import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import com.example.currencyconverter.transactions.data.mapper.toDbo
import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.models.Transaction
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime


class TransactionRepositoryImplTest {

    private val dao: TransactionDao = mock()
    private val repository: TransactionRepository = TransactionRepositoryImpl(dao)

    @Test
    fun `getAllFlow should return list of transactions`() = runTest {
        val expected = listOf(Transaction(
            id = 1,
            from = "RUB",
            to = "EUR",
            fromAmount = 15000.0,
            toAmount = 150.0,
            dateTime = LocalDateTime.now()
        ))
        whenever(dao.getAllAsFlow()).thenReturn(flowOf(expected.map { it.toDbo() }))

        val result = repository.getAllFlow().first()

        assertEquals(expected, result)
        verify(dao).getAllAsFlow()
    }

    @Test
    fun `insert should insert transaction into the database`() = runTest {
        val transaction = Transaction(
            id = 1,
            from = "RUB",
            to = "EUR",
            fromAmount = 15000.0,
            toAmount = 150.0,
            dateTime = LocalDateTime.now()
        )

        repository.insert(transaction)

        verify(dao).insertAll(transaction.toDbo())
    }
}