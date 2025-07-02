package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class GetCurrencySymbolUseCaseTest {
    private val repository: CurrencyRepository = mock()
    private val useCase = GetCurrencySymbolUseCase(repository)

    @Test
    fun `invoke return success currency symbol`() {
        whenever(repository.getCurrencySymbol("EUR")).thenReturn("€")

        val result = useCase("EUR")

        assertEquals("€", result)
        verify(repository).getCurrencySymbol("EUR")
    }

    @Test
    fun `invoke does not find currency symbol return code`() {
        whenever(repository.getCurrencySymbol("ASDASD")).thenReturn("ASDASD")

        val result = useCase("ASDASD")

        assertEquals("ASDASD", result)
        verify(repository).getCurrencySymbol("ASDASD")
    }
}