package com.example.currencyconverter.profile.presentation

import com.example.currencyconverter.profile.domain.models.Account
import androidx.annotation.DrawableRes

data class ProfileState(
    val accounts: List<Account> = emptyList(),
    val selectedIndex: Int = 0,
    @DrawableRes val flagRes: Int? = null,
) {
    val account: Account?
        get() = accounts.getOrNull(selectedIndex)
}