package com.example.currencyconverter.transactions.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TransactionsScreenRoot(viewModel: TransactionsViewModel = hiltViewModel()) {
    val state = viewModel.state

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(state.transactions) { transaction ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "${transaction.from} -> ${transaction.to}")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${transaction.fromAmount} -> ${transaction.toAmount}")
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}