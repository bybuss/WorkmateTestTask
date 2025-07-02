package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.common.utils.Result
import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import com.example.currencyconverter.home.domain.models.Rate
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class LoadRatesUseCaseTest {

    private val repository: CurrencyRepository = mock()
    private val useCase = LoadRatesUseCase(repository)

    @Test
    fun `invoke return success rates value`() = runTest {
        val expected = Result.Success(listOf(Rate("EUR", 1.0)))
        whenever(repository.loadRates("EUR", 1.0)).thenReturn(expected)

        val result = useCase.invoke("EUR", 1.0)

        assertEquals(expected, result)
        verify(repository).loadRates("EUR", 1.0)
    }

    @Test
    fun `invoke return error`() = runTest {
        val expected = Result.Error(title = "title", text = "text")
        whenever(repository.loadRates("ASDASD", 1.0)).thenReturn(expected)

        val result = useCase.invoke("ASDASD", 1.0)

        assertEquals(expected, result)
        verify(repository).loadRates("ASDASD", 1.0)
    }
}