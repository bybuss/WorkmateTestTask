package com.example.currencyconverter.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.profile.domain.use_cases.GetAccountsFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountsFlow: GetAccountsFlowUseCase,
    private val getFlagResId: GetFlagResIdUseCase
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        load()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.Next -> move(1)
            ProfileAction.Prev -> move(-1)
        }
    }

    private fun load() {
        viewModelScope.launch {
            getAccountsFlow().collect { accounts ->
                val index = state.selectedIndex.coerceIn(0, accounts.size - 1)
                val flag = accounts.getOrNull(index)?.let { getFlagResId(it.code) }
                state = state.copy(accounts = accounts, selectedIndex = index, flagRes = flag)
            }
        }
    }

    private fun move(delta: Int) {
        val newIndex = (state.selectedIndex + delta).coerceIn(0, state.accounts.size - 1)
        if (newIndex != state.selectedIndex) {
            val account = state.accounts.getOrNull(newIndex)
            val flag = account?.let { getFlagResId(it.code) }
            state = state.copy(selectedIndex = newIndex, flagRes = flag)
        }
    }
}