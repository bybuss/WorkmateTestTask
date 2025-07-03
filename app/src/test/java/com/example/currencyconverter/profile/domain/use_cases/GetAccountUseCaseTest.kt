package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetAccountUseCaseTest {
    private val repository: AccountRepository = mock()
    private val useCase = GetAccountUseCase(repository)

    @Test
    fun `invoke success get single account by code`() = runTest {
        val expected = Account(code = "RUB", amount = 75000.0)
        whenever(repository.get("RUB")).thenReturn(expected)

        val result = useCase.invoke("RUB")

        assertEquals(expected, result)
        verify(repository).get("RUB")
    }

    @Test
    fun `invoke return null when account does not exist`() = runTest {
        whenever(repository.get("USD")).thenReturn(null)

        val result = useCase.invoke("USD")

        assertEquals(null, result)
        verify(repository).get("USD")
    }
}