package com.example.currencyconverter.home.presentation.exchange

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExchangeScreenRoot(modifier: Modifier = Modifier) {
    val codeTo = "" // FIXME: getting codeTo from navArgs

    Text("Exchange")
    // TODO: codeFrom = code from AccountDbo -> exchange codeFrom to codeTo
}