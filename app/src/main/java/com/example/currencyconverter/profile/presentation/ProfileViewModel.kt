package com.example.currencyconverter.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.profile.domain.use_cases.GetAccountsUseCase
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccounts: GetAccountsUseCase,
    private val getFlagResId: GetFlagResIdUseCase,
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val account = getAccounts().firstOrNull()
            val flag = account?.let { getFlagResId(it.code) }
            state = state.copy(account = account, flagRes = flag)
        }
    }
}