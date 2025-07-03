package com.example.currencyconverter.transactions.domain.use_cases

import com.example.currencyconverter.transactions.domain.dataSource.room.TransactionRepository
import com.example.currencyconverter.transactions.domain.models.Transaction
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class InsertTransactionUseCaseTest {

    private val repository: TransactionRepository = mock()
    private val useCase = InsertTransactionUseCase(repository)

    @Test
    fun `invoke success insert transaction`() = runTest {
        val transaction = Transaction(
            id = 1,
            from = "RUB",
            to = "EUR",
            fromAmount = 15000.0,
            toAmount = 150.0,
            dateTime = LocalDateTime.now()
        )

        useCase.invoke(transaction)

        verify(repository).insert(transaction)
    }

    @Test
    fun `invoke insert multiple transactions`() = runTest {
        val transaction1 = Transaction(
            id = 1,
            from = "RUB",
            to = "EUR",
            fromAmount = 15000.0,
            toAmount = 150.0,
            dateTime = LocalDateTime.now()
        )
        val transaction2 = Transaction(
            id = 2,
            from = "RUB",
            to = "EUR",
            fromAmount = 15.0,
            toAmount = 1.5,
            dateTime = LocalDateTime.now()
        )

        useCase.invoke(transaction1)
        useCase.invoke(transaction2)

        verify(repository).insert(transaction1)
        verify(repository).insert(transaction2)
    }
}