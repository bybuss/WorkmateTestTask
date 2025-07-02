package com.example.currencyconverter.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.common.design_system.CustomSnackbarHost
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.home.presentation.currencies.CurrencyListScreenRoot
import com.example.currencyconverter.home.presentation.exchange.ExchangeScreenRoot

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) },
        bottomBar = { BottomBar(navController = navController) },
        contentColor = CustomTheme.colors.primaryText ,
        containerColor = CustomTheme.colors.background
    ) { innerPadding ->
        NavHost(
            startDestination = Screens.Currencies,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            animatedTransition<Screens.Currencies> {
                CurrencyListScreenRoot(
                    navController = navController,
                    snackBarHostState = snackbarHostState,
                )
            }
            animatedTransition<Screens.Exchange> { backStackEntry ->
                ExchangeScreenRoot(navController = navController, backStackEntry = backStackEntry)
            }
            animatedTransition<Screens.TransactionHistory> { }
            animatedTransition<Screens.Profile> { }
        }
    }

}