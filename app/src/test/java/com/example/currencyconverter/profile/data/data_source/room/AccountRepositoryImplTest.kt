package com.example.currencyconverter.profile.data.data_source.room

import com.example.currencyconverter.profile.data.data_source.room.dao.AccountDao
import com.example.currencyconverter.profile.data.mapper.toDbo
import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AccountRepositoryImplTest {
    private val dao: AccountDao = mock()
    private val repository: AccountRepository = AccountRepositoryImpl(dao)

    @Test
    fun `getAllFlow should return list of accounts`() = runTest {
        val expected = listOf(Account(code = "RUB", amount = 75000.0))
        whenever(dao.getAllAsFlow()).thenReturn(flowOf(expected.map { it.toDbo() }))

        val result = repository.getAllFlow().first()

        assertEquals(expected, result)
        verify(dao).getAllAsFlow()
    }

    @Test
    fun `get should return account when exists`() = runTest {
        val expected = Account(code = "RUB", amount = 75000.0)
        whenever(dao.getAll()).thenReturn(listOf(expected.toDbo()))

        val result = repository.get("RUB")

        assertEquals(expected, result)
        verify(dao).getAll()
    }

    @Test
    fun `get should return null when account does not exist`() = runTest {
        whenever(dao.getAll()).thenReturn(emptyList())

        val result = repository.get("USD")

        assertEquals(null, result)
        verify(dao).getAll()
    }
}