package com.example.currencyconverter.profile.presentation

sealed interface ProfileAction {
    data object Prev : ProfileAction
    data object Next : ProfileAction
}