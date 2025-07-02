package com.example.currencyconverter.home.presentation.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.example.currencyconverter.common.design_system.LoadingIndicator
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
                ExchangeAction.Back -> navController.popBackStack()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

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
        args?.let {
            Text(text = "${args.codeFrom} -> ${args.codeTo}")
            Text(text = "${args.amountFrom} ${args.codeFrom} = ${args.amountTo} ${args.codeTo}")
            Button(onClick = {
                onAction(ExchangeAction.Exchange(
                    codeFrom = args.codeFrom,
                    codeTo = args.codeTo,
                    amountFrom = args.amountFrom,
                    amountTo = args.amountTo
                ))
                onAction(ExchangeAction.Back)
            }) {
                Text("Buy ${args.codeTo} for ${args.codeFrom}")
            }
        } ?: run {
            LoadingIndicator()
        }
    }
}