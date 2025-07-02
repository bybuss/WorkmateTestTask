package com.example.currencyconverter.transactions.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.common.utils.format
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.transactions.domain.models.TransactionItem
import com.example.currencyconverter.transactions.domain.use_cases.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
    private val getFlagResId: GetFlagResIdUseCase
) : ViewModel() {

    var state by mutableStateOf(TransactionsState())
        private set

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            val items = getTransactions().map { tr ->
                TransactionItem(
                    id = tr.id,
                    flagFrom = getFlagResId(tr.from),
                    flagTo = getFlagResId(tr.to),
                    pairText = "${tr.from} -> ${tr.to}",
                    amountText = "${tr.fromAmount.format()} -> ${tr.toAmount.format()}"
                )
            }
            state = state.copy(transactions = items)
        }
    }
}