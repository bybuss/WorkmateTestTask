package com.example.currencyconverter.home.presentation.currencies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.common.utils.UiState
import com.example.currencyconverter.common.utils.toUiState
import com.example.currencyconverter.home.domain.models.CurrencyItem
import com.example.currencyconverter.home.domain.use_cases.GetCurrencyNameUseCase
import com.example.currencyconverter.home.domain.use_cases.GetCurrencySymbolUseCase
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.home.domain.use_cases.LoadRatesUseCase
import com.example.currencyconverter.profile.domain.use_cases.GetAccountsFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val loadRates: LoadRatesUseCase,
    private val getCurrencyName: GetCurrencyNameUseCase,
    private val getFlagResId: GetFlagResIdUseCase,
    private val getCurrencySymbol: GetCurrencySymbolUseCase,
    private val getAccounts: GetAccountsFlowUseCase,
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
                state = state.copy(baseCurrency = action.code)
                startUpdates()
            }
            is CurrencyListAction.ChangeAmount -> state = state.copy(amountInput = action.value)
            CurrencyListAction.StartEdit -> state = state.copy(isEditing = true)
            CurrencyListAction.ClearAmount -> {
                state = state.copy(amountInput = "1", isEditing = false)
                startUpdates()
            }
        }
    }

    private fun startUpdates() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            if (state.items !is UiState.Success) {
                state = state.copy(items = UiState.Loading)
            }
            while (isActive) {
                val base = state.baseCurrency
                val amount = state.amountInput.toDoubleOrNull() ?: 1.0

                val accountAmounts = getAccounts()
                    .first()
                    .groupBy { it.code }
                    .mapValues { entry ->
                        entry.value.sumOf { it.amount }
                    }
                val response = loadRates(base, amount).toUiState()

                when (response) {
                    is UiState.Success -> {
                        val items = response.data.map { rate ->
                            val name = getCurrencyName(rate.currency)
                            val flag = getFlagResId(rate.currency)
                            val symbol = getCurrencySymbol(rate.currency)
                            CurrencyItem(
                                code = rate.currency,
                                name = name,
                                flagRes = flag,
                                rate = rate.value,
                                symbol = symbol
                            )
                        }

                        val filtered = if (state.isEditing) {
                            items.filter { item ->
                                item.code == state.baseCurrency ||
                                        (accountAmounts[item.code] ?: 0.0) >= item.rate
                            }
                        } else items

                        state = state.copy(items = UiState.Success(filtered))
                    }
                    is UiState.Error -> state = state.copy(items = response)
                    else -> {}
                }

                delay(1000)
            }
        }
    }
}