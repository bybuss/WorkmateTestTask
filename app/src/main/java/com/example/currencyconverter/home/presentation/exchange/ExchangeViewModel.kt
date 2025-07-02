package com.example.currencyconverter.home.presentation.exchange

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.common.utils.format
import com.example.currencyconverter.home.domain.use_cases.GetCurrencyNameUseCase
import com.example.currencyconverter.home.domain.use_cases.GetCurrencySymbolUseCase
import com.example.currencyconverter.home.domain.use_cases.GetFlagResIdUseCase
import com.example.currencyconverter.home.domain.use_cases.LoadRatesUseCase
import com.example.currencyconverter.navigation.Screens
import com.example.currencyconverter.profile.domain.models.Account
import com.example.currencyconverter.profile.domain.use_cases.InsertAccountUseCase
import com.example.currencyconverter.profile.domain.use_cases.UpdateAccountAmountUseCase
import com.example.currencyconverter.transactions.domain.models.Transaction
import com.example.currencyconverter.transactions.domain.use_cases.InsertTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.currencyconverter.common.utils.Result
import com.example.currencyconverter.profile.domain.use_cases.GetAccountUseCase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getAccount: GetAccountUseCase,
    private val insertAccount: InsertAccountUseCase,
    private val updateAccountAmount: UpdateAccountAmountUseCase,
    private val loadRates: LoadRatesUseCase,
    private val insertTransaction: InsertTransactionUseCase,
    private val getCurrencyName: GetCurrencyNameUseCase,
    private val getCurrencySymbol: GetCurrencySymbolUseCase,
    private val getFlagResId: GetFlagResIdUseCase,
) : ViewModel() {

    var state by mutableStateOf(ExchangeState())
        private set

    fun onAction(action: ExchangeAction) {
        when (action) {
            is ExchangeAction.SetArgs -> setupArgs(action.args)
            ExchangeAction.Confirm -> state.args?.let { args ->
                exchange(args.codeFrom, args.codeTo, args.amountFrom, args.amountTo)
            }
            else -> Unit
        }
    }

    private fun setupArgs(args: Screens.Exchange) {
        val fromName = getCurrencyName(args.codeFrom)
        val toName = getCurrencyName(args.codeTo)
        val fromSymbol = getCurrencySymbol(args.codeFrom)
        val toSymbol = getCurrencySymbol(args.codeTo)
        state = state.copy(
            args = args,
            title = "$fromName to $toName",
            rateText = "$toSymbol = $fromSymbol${(args.amountFrom / args.amountTo).format()}",
            fromFlag = getFlagResId(args.codeFrom),
            toFlag = getFlagResId(args.codeTo),
            fromAmountText = "-$fromSymbol${args.amountFrom.format()}",
            toAmountText = "+$toSymbol${args.amountTo.format()}",
            buttonText = "Buy $toName for $fromName",
            errorMessage = null
        )
    }

    private fun exchange(codeFrom: String, codeTo: String, amountFrom: Double, amountTo: Double) {
        viewModelScope.launch {
            val rubAccount = getAccount("RUB")
            if (rubAccount == null) {
                state = state.copy(errorMessage = "Account not found")
                return@launch
            }

            val rubCost = if (codeFrom == "RUB") {
                amountFrom
            } else {
                when (val result = loadRates(codeFrom, amountFrom)) {
                    is Result.Success -> {
                        result.data.firstOrNull { it.currency == "RUB" }?.value
                            ?: amountFrom
                    }
                    is Result.Error -> amountFrom
                }
            }

            if (rubAccount.amount < rubCost) {
                state = state.copy(errorMessage = "Not enough funds")
                return@launch
            }

            updateAccountAmount("RUB", rubAccount.amount - rubCost)

            val fromAccount = getAccount(codeFrom)
            if (fromAccount != null && codeFrom != "RUB") {
                updateAccountAmount(codeFrom, fromAccount.amount - amountFrom)
            }

            val toAccount = getAccount(codeTo)
            if (toAccount == null) {
                insertAccount(Account(codeTo, amountTo))
            } else {
                updateAccountAmount(codeTo, toAccount.amount + amountTo)
            }

            insertTransaction(
                Transaction(
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