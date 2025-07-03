package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class UpdateAccountAmountUseCaseTest {
    private val repository: AccountRepository = mock()
    private val useCase = UpdateAccountAmountUseCase(repository)

    @Test
    fun `invoke success update account amount`() = runTest {
        val code = "RUB"
        val newAmount = 100000.0

        useCase.invoke(code, newAmount)

        verify(repository).updateAmount(code, newAmount)
    }

    @Test
    fun `invoke multiple updates for different accounts`() = runTest {
        val code1 = "RUB"
        val code2 = "USD"
        val amount1 = 100000.0
        val amount2 = 2000.0

        useCase.invoke(code1, amount1)
        useCase.invoke(code2, amount2)

        verify(repository).updateAmount(code1, amount1)
        verify(repository).updateAmount(code2, amount2)
    }
}