package com.example.currencyconverter.home.presentation.currencies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.common.design_system.LoadingIndicator
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.common.utils.UiState

@Composable
fun CurrencyListScreenRoot(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    viewModel: CurrencyListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val itemsState = state.items

    LaunchedEffect(state) {
        if (itemsState is UiState.Error) {
            snackBarHostState.showSnackbar(itemsState.title)
        }
    }

    when (itemsState) {
        UiState.Loading -> {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomTheme.colors.background)
            )
        }
        is UiState.Success -> CurrencyList(
            state = viewModel.state,
            onAction = { action ->
                when (action) {
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        else -> {}
    }
}

@Composable
fun CurrencyList(
    state: CurrencyListState,
    onAction: (CurrencyListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items((state.items as UiState.Success).data) { item ->
            val isBase = item.code == state.baseCurrency
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isBase) CustomTheme.colors.selectedCard else CustomTheme.colors.cardBackground
                    )
                    .clickable { onAction(CurrencyListAction.SelectCurrency(item.code)) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(item.flagRes),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.code, style = CustomTheme.typography.h3, color = CustomTheme.colors.primaryText)
                    Text(text = item.name, style = CustomTheme.typography.caption, color = CustomTheme.colors.secondaryText)
                }
                if (isBase && state.isEditing) {
                    TextField(
                        value = state.amountInput,
                        onValueChange = { onAction(CurrencyListAction.ChangeAmount(it)) },
                        modifier = Modifier.width(100.dp)
                    )
                    IconButton(onClick = { onAction(CurrencyListAction.ClearAmount) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                } else {
                    Text(
                        text = String.format("%.2f", item.rate),
                        style = CustomTheme.typography.h3,
                        color = CustomTheme.colors.primaryText,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable(enabled = isBase) {
                                if (isBase) onAction(CurrencyListAction.StartEdit)
                            }
                    )
                }
            }
        }
    }
}