package com.example.currencyconverter.common.utils

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val title: String, val text: String) : UiState<Nothing>
}