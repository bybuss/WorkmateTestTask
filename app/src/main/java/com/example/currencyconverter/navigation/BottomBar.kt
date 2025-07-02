package com.example.currencyconverter.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.currencyconverter.common.design_system.theme.CustomTheme

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items: List<BottomBarDestinations> = listOf(
        BottomBarDestinations.CURRENCIES,
        BottomBarDestinations.TRANSACTION_HISTORY,
        BottomBarDestinations.PROFILE
    )

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = CustomTheme.colors.accent,
            thickness = 1.dp
        )
        NavigationBar(
            containerColor = CustomTheme.colors.background,
            contentColor = CustomTheme.colors.primaryText,
            modifier = modifier
        ) {

            items.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(destination.screen::class)
                } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.screen) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = stringResource(destination.label)
                        )
                    },
                    label = { Text(text = stringResource(destination.label)) },
                    modifier = Modifier,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CustomTheme.colors.primaryText,
                        selectedTextColor = CustomTheme.colors.primaryText,
                        unselectedIconColor = CustomTheme.colors.secondaryText,
                        unselectedTextColor = CustomTheme.colors.secondaryText,
                        disabledIconColor = CustomTheme.colors.secondaryText,
                        disabledTextColor = CustomTheme.colors.secondaryText,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}