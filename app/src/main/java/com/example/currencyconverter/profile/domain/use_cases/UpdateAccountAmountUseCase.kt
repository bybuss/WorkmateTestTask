package com.example.currencyconverter.profile.domain.use_cases

import com.example.currencyconverter.profile.domain.data_source.room.AccountRepository
import javax.inject.Inject

class UpdateAccountAmountUseCase @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(code: String, amount: Double) =
        repository.updateAmount(code, amount)
}