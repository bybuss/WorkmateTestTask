package com.example.currencyconverter.home.data.data_source.remote

import com.example.currencyconverter.home.data.data_source.remote.dto.RateDto
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


class RemoteRatesServiceImplTest {

    private val service = RemoteRatesServiceImpl()

    @Test
    fun `getRates should return rates with correct calculation`() = runTest {
        val baseCurrency = "USD"
        val amount = 100.0

        val expected = listOf(
            RateDto("USD", amount),
            RateDto("EUR", 0.86 * amount),
            RateDto("GBP", 0.77 * amount)
        )

        val result = service.getRates(baseCurrency, amount)

        assertEquals(expected[0], result[0])
        assertEquals(expected[1].currency, result[1].currency)
    }
}