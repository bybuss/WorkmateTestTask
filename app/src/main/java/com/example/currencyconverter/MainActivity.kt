package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.example.currencyconverter.common.design_system.theme.CurrencyConverterTheme
import com.example.currencyconverter.common.design_system.theme.CustomTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Box(modifier = Modifier.fillMaxSize().background(CustomTheme.colors.background))
            }
        }
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }
}