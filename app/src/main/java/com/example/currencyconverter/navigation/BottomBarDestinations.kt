package com.example.currencyconverter.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.currencyconverter.R

enum class BottomBarDestinations(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val screen: Screens
) {
    CURRENCIES(
        icon = R.drawable.ic_currencies,
        label = R.string.nav_currencies,
        screen = Screens.Currencies
    ),
    TRANSACTION_HISTORY(
        icon = R.drawable.ic_transaction,
        label = R.string.nav_transactions,
        screen = Screens.TransactionHistory
    ),
    PROFILE(
        icon = R.drawable.ic_profile,
        label = R.string.nav_profile,
        screen = Screens.Profile
    )
}