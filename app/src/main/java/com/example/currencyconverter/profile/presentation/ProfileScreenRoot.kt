package com.example.currencyconverter.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.common.design_system.theme.CustomTheme
import com.example.currencyconverter.common.utils.format

@Composable
fun ProfileScreenRoot(viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.state

    state.account?.let { account ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            state.flagRes?.let { res ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.onAction(ProfileAction.Prev) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = CustomTheme.colors.primaryText
                        )
                    }
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    IconButton(onClick = { viewModel.onAction(ProfileAction.Next) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = CustomTheme.colors.primaryText
                        )
                    }
                }
            }
            Text(
                text = "Balance: ${account.code} ${account.amount.format()}",
                style = CustomTheme.typography.h3,
                color = CustomTheme.colors.primaryText
            )
        }
    }
}