package com.example.currencyconverter.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.common.design_system.theme.CustomTheme

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        contentColor = CustomTheme.colors.background,
        containerColor = CustomTheme.colors.primaryText
    ) { innerPadding ->
        NavHost(
            startDestination = Screens.Currencies,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            animatedTransition<Screens.Currencies> { }
            animatedTransition<Screens.Exchange> { }
            animatedTransition<Screens.TransactionHistory> { }
            animatedTransition<Screens.Profile> { }
        }
    }

}