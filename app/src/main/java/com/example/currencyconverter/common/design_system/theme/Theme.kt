package com.example.currencyconverter.common.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors else lightColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTextStyles provides AppTextStyles,
        content = content
    )
}

object CustomTheme {
    val colors: AppColors
        @Composable get() = LocalColors.current
    val typography: AppTextStyles
        @Composable get() = LocalTextStyles.current
}