package com.example.currencyconverter.home.presentation.exchange

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.profile.domain.models.Account
import com.example.currencyconverter.profile.domain.use_cases.GetAccountUseCase
import com.example.currencyconverter.profile.domain.use_cases.InsertAccountUseCase
import com.example.currencyconverter.profile.domain.use_cases.UpdateAccountAmountUseCase
import com.example.currencyconverter.transactions.data.data_source.room.dao.TransactionDao
import com.example.currencyconverter.transactions.data.data_source.room.dbo.TransactionDbo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getAccount: GetAccountUseCase,
    private val insertAccount: InsertAccountUseCase,
    private val updateAccountAmount: UpdateAccountAmountUseCase,
    private val transactionDao: TransactionDao,
) : ViewModel() {

    var state by mutableStateOf(ExchangeState())
        private set

    fun onAction(action: ExchangeAction) {
        when (action) {
            is ExchangeAction.Exchange -> exchange(
                codeFrom = action.codeFrom,
                codeTo = action.codeTo,
                amountFrom = action.amountFrom,
                amountTo = action.amountTo
            )
            is ExchangeAction.SetArgs -> state = state.copy(args = action.args, errorMessage = null)
            else -> Unit
        }
    }

    private fun exchange(codeFrom: String, codeTo: String, amountFrom: Double, amountTo: Double) {
        viewModelScope.launch {
            val fromAccount = getAccount(codeFrom)
            if (fromAccount == null || fromAccount.amount < amountFrom) {
                state = state.copy(errorMessage = "Not enough funds")
                return@launch
            } else {
                updateAccountAmount(codeFrom, fromAccount.amount - amountFrom)
            }

            val toAccount = getAccount(codeTo)
            if (toAccount == null) {
                insertAccount(Account(codeTo, amountTo))
            } else {
                updateAccountAmount(codeTo, toAccount.amount + amountTo)
            }

            transactionDao.insertAll(
                TransactionDbo(
                    id = 0,
                    from = codeFrom,
                    to = codeTo,
                    fromAmount = amountFrom,
                    toAmount = amountTo,
                    dateTime = LocalDateTime.now()
                )
            )
            state = state.copy(errorMessage = null)
        }
    }
}