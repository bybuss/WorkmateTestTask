package com.example.currencyconverter.transactions.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.transactions.domain.models.TransactionItem

@Composable
fun TransactionsScreenRoot(viewModel: TransactionsViewModel = hiltViewModel()) {
    val state = viewModel.state

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(state.transactions) { transaction ->
            TransactionItemView(transaction)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun TransactionItemView(transaction: TransactionItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.cardBackground)
            .padding(8.dp)
    ) {
        DoubleFlagAvatar(transaction.flagFrom, transaction.flagTo)
        Text(
            text = transaction.pairText,
            style = CustomTheme.typography.h4,
            color = CustomTheme.colors.primaryText
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = transaction.amountText,
            style = CustomTheme.typography.h4,
            color = CustomTheme.colors.primaryText
        )
    }
}

@Composable
private fun DoubleFlagAvatar(flagFrom: Int?, flagTo: Int?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(40.dp)) {
        if (flagFrom != 0) {
            Image(
                painter = painterResource(flagFrom ?: R.drawable.flag_unknown),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clip(androidx.compose.foundation.shape.CircleShape))
        }
        if (flagTo != 0) {
            Image(
                painter = painterResource(flagTo ?: R.drawable.flag_unknown),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .padding(start = 20.dp))
        }
    }
}