package com.example.currencyconverter.transactions.domain.use_cases

import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.models.Transaction
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class GetTransactionsUseCaseTest {

    private val repository: TransactionRepository = mock()
    private val useCase = GetTransactionsUseCase(repository)

    @Test
    fun `invoke success get flow list of transactions`() {
        val expected = flowOf(listOf(
            Transaction(
                id = 1,
                from = "RUB",
                to = "EUR",
                fromAmount = 15000.0,
                toAmount = 150.0,
                dateTime = LocalDateTime.now()
            ))
        )
        whenever(repository.getAllFlow()).thenReturn(expected)

        val result = useCase.invoke()

        assertEquals(expected, result)
        verify(repository).getAllFlow()
    }

    @Test
    fun `invoke return empty flow when no transactions`() {
        val expected = flowOf(emptyList<Transaction>())
        whenever(repository.getAllFlow()).thenReturn(expected)

        val result = useCase.invoke()

        assertEquals(expected, result)
        verify(repository).getAllFlow()
    }
}