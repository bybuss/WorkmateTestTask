package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class InsertAccountUseCaseTest {
    private val repository: AccountRepository = mock()
    private val useCase = InsertAccountUseCase(repository)

    @Test
    fun `invoke success insert account`() = runTest {
        val account = Account(code = "RUB", amount = 75000.0)

        useCase.invoke(account)

        verify(repository).insert(account)
    }

    @Test
    fun `invoke insert multiple accounts`() = runTest {
        val account1 = Account(code = "RUB", amount = 75000.0)
        val account2 = Account(code = "USD", amount = 1000.0)

        useCase.invoke(account1)
        useCase.invoke(account2)

        verify(repository).insert(account1)
        verify(repository).insert(account2)
    }
}