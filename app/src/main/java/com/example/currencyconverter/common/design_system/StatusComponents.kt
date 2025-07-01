package com.example.currencyconverter.common.design_system

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.common.design_system.theme.CustomTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isIndicatorOnly: Boolean = false,
    color: Color = CustomTheme.colors.button
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column {
            CircularProgressIndicator(
                color = color,
                modifier = Modifier
                    .size(40.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                ,
                strokeWidth = 2.dp
            )
            if (!isIndicatorOnly) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.loading),
                    style = CustomTheme.typography.p,
                    color = CustomTheme.colors.button,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = {data ->
            CustomSnackbarContent(snackbarData = data)
        }
    )
}

@Composable
private fun CustomSnackbarContent(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        color = CustomTheme.colors.button
    ) {
        Row(
            modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = CustomTheme.colors.buttonText
            )
            Text(
                text = snackbarData.visuals.message,
                modifier = Modifier.weight(1f),
                color = CustomTheme.colors.buttonText,
                style = CustomTheme.typography.h3,
                textAlign = TextAlign.Start
            )
        }
    }
}