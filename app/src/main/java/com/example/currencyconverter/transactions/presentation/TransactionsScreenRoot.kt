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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.transactions.domain.models.TransactionItem

@Composable
fun TransactionsScreenRoot(viewModel: TransactionsViewModel = hiltViewModel()) {
    val state = viewModel.state

    if (state.transactions.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_transactions),
                style = CustomTheme.typography.h4,
                color = CustomTheme.colors.primaryText
            )
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.transactions) { transaction ->
                TransactionItemView(transaction)
                Spacer(modifier = Modifier.height(4.dp))
            }
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
    val leftShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width / 2f, 0f)
        lineTo(size.width / 2f, size.height)
        lineTo(0f, size.height)
        close()
    }
    val rightShape = GenericShape { size, _ ->
        moveTo(size.width / 2f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(size.width / 2f, size.height)
        close()
    }
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(flagFrom ?: R.drawable.flag_unknown),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = 45f
                    clip = true
                    shape = leftShape
                }
                .rotate(-45f),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(flagTo ?: R.drawable.flag_unknown),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = 45f
                    clip = true
                    shape = rightShape
                }
                .rotate(-45f),
            contentScale = ContentScale.Crop
        )
    }
}