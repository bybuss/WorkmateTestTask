package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetCurrencyNameUseCaseTest {

    private val repository: CurrencyRepository = mock()
    private val useCase = GetCurrencyNameUseCase(repository)

    @Test
    fun `invoke return success currency name`()  {
        whenever(repository.getCurrencyName("EUR")).thenReturn("Euro")

        val result = useCase("EUR")

        assertEquals("Euro", result)
        verify(repository).getCurrencyName("EUR")
    }

    @Test
    fun `invoke does not find currency name return code`() {
        whenever(repository.getCurrencyName("ASDASD")).thenReturn("ASDASD")

        val result = useCase("ASDASD")

        assertEquals("ASDASD", result)
        verify(repository).getCurrencyName("ASDASD")
    }
}