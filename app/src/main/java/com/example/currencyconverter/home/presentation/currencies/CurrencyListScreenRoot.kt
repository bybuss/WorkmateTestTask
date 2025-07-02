package com.example.currencyconverter.home.presentation.currencies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.common.design_system.LoadingIndicator
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.common.utils.UiState
import com.example.currencyconverter.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun CurrencyListScreenRoot(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    viewModel: CurrencyListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val itemsState = state.items
    val scope = rememberCoroutineScope()

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
                    is CurrencyListAction.NavigateToExchange -> {
                        if (action.codeFrom == action.codeTo) {
                            scope.launch {
                                snackBarHostState.showSnackbar("Cannot convert same currency")
                            }
                        } else {
                            navController.navigate(
                                Screens.Exchange(
                                    codeFrom = action.codeFrom,
                                    codeTo = action.codeTo,
                                    amountFrom = action.amountFrom,
                                    amountTo = action.amountTo
                                )
                            )
                        }
                    }
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
                        if (isBase) CustomTheme.colors.selectedCard
                        else CustomTheme.colors.cardBackground
                    )
                    .clickable(enabled = !state.isEditing && item.code != state.baseCurrency) {
                        onAction(
                            CurrencyListAction.NavigateToExchange(
                                codeFrom = item.code,
                                codeTo = state.baseCurrency,
                                amountFrom = item.rate,
                                amountTo = state.amountInput.toDoubleOrNull() ?: 0.0
                            )
                        )
                        onAction(CurrencyListAction.StartEdit)
                    }
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
                    Text(
                        text = item.code,
                        style = CustomTheme.typography.h3,
                        color = CustomTheme.colors.primaryText
                    )
                    Text(
                        text = item.name,
                        style = CustomTheme.typography.caption,
                        color = CustomTheme.colors.secondaryText
                    )
                }
                if (isBase && state.isEditing) {
                    TextField(
                        value = state.amountInput,
                        onValueChange = { onAction(CurrencyListAction.ChangeAmount(it)) },
                        modifier = Modifier.width(100.dp),
                        singleLine = true,
                        leadingIcon = { Text(text = item.symbol) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            onAction(CurrencyListAction.NavigateToExchange(
                                codeFrom = item.code,
                                codeTo = state.baseCurrency,
                                amountFrom = item.rate,
                                amountTo = state.amountInput.toDoubleOrNull() ?: 0.0
                            ))
                        }),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CustomTheme.colors.cardBackground,
                            unfocusedContainerColor = CustomTheme.colors.cardBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = CustomTheme.typography.h3.copy(color = CustomTheme.colors.primaryText),
                        trailingIcon = {
                            IconButton(onClick = { onAction(CurrencyListAction.ClearAmount) }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = CustomTheme.colors.primaryText
                                )
                            }
                        }
                    )
                    IconButton(onClick = {
                        onAction(CurrencyListAction.NavigateToExchange(
                            codeFrom = item.code,
                            codeTo = state.baseCurrency,
                            amountFrom = item.rate,
                            amountTo = state.amountInput.toDoubleOrNull() ?: 0.0
                        ))
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = CustomTheme.colors.primaryText
                        )
                    }
                } else {
                    Text(
                        text = item.symbol + String.format("%.2f", item.rate),
                        style = CustomTheme.typography.h3,
                        color = CustomTheme.colors.primaryText,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable(enabled = isBase) {
                                if (isBase) onAction(CurrencyListAction.StartEdit)
                            }
                    )
                    if (state.isEditing && item.code != state.baseCurrency) {
                        IconButton(onClick = {
                            onAction(CurrencyListAction.NavigateToExchange(
                                codeFrom = item.code,
                                codeTo = state.baseCurrency,
                                amountFrom = item.rate,
                                amountTo = state.amountInput.toDoubleOrNull() ?: 0.0
                            ))
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = CustomTheme.colors.primaryText
                            )
                        }
                    }
                }
            }
        }
    }
}