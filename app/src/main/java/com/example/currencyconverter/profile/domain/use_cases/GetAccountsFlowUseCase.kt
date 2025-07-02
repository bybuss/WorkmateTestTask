package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsFlowUseCase @Inject constructor(private val repository: AccountRepository) {

    operator fun invoke(): Flow<List<Account>> = repository.getAllFlow()
}