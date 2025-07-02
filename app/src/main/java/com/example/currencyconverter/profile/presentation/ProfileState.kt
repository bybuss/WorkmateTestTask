package com.example.currencyconverter.profile.presentation

import com.example.currencyconverter.profile.domain.models.Account
import androidx.annotation.DrawableRes

data class ProfileState(
    val account: Account? = null,
    @DrawableRes val flagRes: Int? = null,
)