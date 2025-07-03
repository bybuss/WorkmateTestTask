package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class GetAccountsFlowUseCaseTest {
    private val repository: AccountRepository = mock()
    private val useCase = GetAccountsFlowUseCase(repository)

    @Test
    fun `invoke return success flow list of accounts`() {

        val expected =  flowOf(listOf(Account(code = "RUB", amount = 75000.0)))
        whenever(repository.getAllFlow()).thenReturn(expected)

        val result = useCase.invoke()

        assertEquals(expected, result)
        verify(repository).getAllFlow()
    }
}