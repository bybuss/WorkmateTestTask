package com.example.currencyconverter.home.presentation.currencies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.common.utils.UiState
import com.example.currencyconverter.common.utils.toUiState
import com.example.currencyconverter.home.domain.CurrencyRepository
import com.example.currencyconverter.home.domain.models.CurrencyItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    var state by mutableStateOf(CurrencyListState())
        private set
    private var updateJob: Job? = null

    init {
        startUpdates()
    }

    fun onAction(action: CurrencyListAction) {
        when (action) {
            is CurrencyListAction.SelectCurrency -> {
                state.copy(baseCurrency = action.code)
                startUpdates()
            }
            is CurrencyListAction.ChangeAmount -> {
                state.copy(amountInput = action.value)
                startUpdates()
            }
            CurrencyListAction.StartEdit -> state.copy(isEditing = true)
            CurrencyListAction.ClearAmount -> {
                state.copy(amountInput = "1", isEditing = false)
                startUpdates()
            }
        }
    }

    private fun startUpdates() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (isActive) {
                val base = state.baseCurrency
                val amount = state.amountInput.toDoubleOrNull() ?: 1.0

                state.copy(items = UiState.Loading)

                val response = repository.loadRates(base, amount).toUiState()

                when (response) {
                    is UiState.Success -> {
                        val items = response.data.map { rate ->
                            val name = repository.getCurrencyName(rate.currency)
                            val flag = repository.getFlagResId(rate.currency)
                            CurrencyItem(
                                code = rate.currency,
                                name = name,
                                flagRes = flag,
                                rate = rate.value,
                            )
                        }
                        state.copy(items = UiState.Success(items))
                    }
                    is UiState.Error -> state = state.copy(items = response)
                    else -> {}
                }

                delay(1000)
            }
        }
    }
}