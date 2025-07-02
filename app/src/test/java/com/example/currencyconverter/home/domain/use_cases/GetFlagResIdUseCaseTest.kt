package com.example.currencyconverter.home.domain.use_cases

import com.example.currencyconverter.R
import com.example.currencyconverter.home.domain.data_source.remote.CurrencyRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetFlagResIdUseCaseTest {

    private val repository: CurrencyRepository = mock()
    private val useCase = GetFlagResIdUseCase(repository)


    @Test
    fun `invoke success return flag resId`() {
        whenever(repository.getFlagResId("EUR")).thenReturn(123)

        val result = useCase("EUR")

        assertEquals(123, result)
        verify(repository).getFlagResId("EUR")
    }

    @Test
    fun `invoke returns unknown flag for errors`() {
        whenever(repository.getFlagResId("ASDASD")).thenReturn(R.drawable.flag_unknown)

        val result = useCase("ASDASD")

        assertEquals(R.drawable.flag_unknown, result)
        verify(repository).getFlagResId("ASDASD")
    }
}