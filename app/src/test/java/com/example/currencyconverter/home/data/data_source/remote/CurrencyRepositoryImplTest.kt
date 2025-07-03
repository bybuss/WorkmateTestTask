package com.example.currencyconverter.home.data.data_source.remote

import android.content.Context
import com.example.currencyconverter.home.data.data_source.remote.dto.RateDto
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import com.example.currencyconverter.common.utils.Result
import junit.framework.TestCase.assertEquals
import org.mockito.kotlin.verify

class CurrencyRepositoryImplTest {

    private val service: RatesService = mock()
    private val context: Context = mock()
    private val repository = CurrencyRepositoryImpl(service, context)

    @Test
    fun `loadRates return success with correct data`() = runTest {
        val base = "USD"
        val amount = 100.0
        val expected = listOf(RateDto("EUR", 0.86))

        whenever(service.getRates(base, amount)).thenReturn(expected)

        val result = repository.loadRates(base, amount)

        assert(result is Result.Success)
        assertEquals((result as Result.Success).data[0].currency, "EUR")
        assertEquals(result.data[0].value, 0.86)
        verify(service).getRates(base, amount)
    }
}