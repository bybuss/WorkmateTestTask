package com.example.currencyconverter.home.presentation.exchange

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.example.currencyconverter.common.design_system.LoadingIndicator
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.navigation.Screens

@Composable
fun ExchangeScreenRoot(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    snackBarHostState: SnackbarHostState,
    viewModel: ExchangeViewModel = hiltViewModel()
) {
    val args = backStackEntry.toRoute<Screens.Exchange>()
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.onAction(ExchangeAction.SetArgs(args))
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackBarHostState.showSnackbar(it) }
    }

    ExchangeScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ExchangeAction.Back -> {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("clear_edit", true)
                    navController.popBackStack()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExchangeScreen(
    state: ExchangeState,
    onAction: (ExchangeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        val args = state.args
        TopAppBar(
            title = { Text(text = state.title) },
            navigationIcon = {
                IconButton(onClick = { onAction(ExchangeAction.Back) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                containerColor = CustomTheme.colors.background,
                titleContentColor = CustomTheme.colors.primaryText,
                navigationIconContentColor = CustomTheme.colors.primaryText
            )
        )

        args?.let {
            Text(
                text = state.rateText,
                style = CustomTheme.typography.caption,
                color = CustomTheme.colors.secondaryText,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CurrencyAmountCard(flagRes = state.fromFlag, text = state.fromAmountText)
                CurrencyAmountCard(flagRes = state.toFlag, text = state.toAmountText)
            }
            Button(
                onClick = {
                    onAction(ExchangeAction.Confirm)
                    onAction(ExchangeAction.Back)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(state.buttonText)
            }
        } ?:LoadingIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun CurrencyAmountCard(
    flagRes: Int?,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        flagRes?.takeIf { it != 0 }?.let { res ->
            Image(
                painter = painterResource(res),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = text,
            style = CustomTheme.typography.h3,
            color = CustomTheme.colors.primaryText,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}