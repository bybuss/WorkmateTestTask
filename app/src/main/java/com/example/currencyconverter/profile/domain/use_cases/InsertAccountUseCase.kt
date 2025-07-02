package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import com.example.currencyconverter.profile.domain.models.Account
import javax.inject.Inject

class InsertAccountUseCase @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(account: Account) = repository.insert(account)
}