package com.example.currencyconverter.common.design_system.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val cardBackground: Color,
    val selectedCard: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val accent: Color,
    val button: Color,
    val buttonText: Color
)

val LocalColors = compositionLocalOf { lightColors }

val lightColors = AppColors(
    background = Color(0xFFFDF5E6),
    cardBackground = Color(0xFFFFFAF0),
    selectedCard = Color(0xFFFFF0D6),
    primaryText = Color(0xFF5C4B3A),
    secondaryText = Color(0xFF8D7B68),
    accent = Color(0xFFD4A96A),
    button = Color(0xFFD4B88C),
    buttonText = Color(0xFF5C4B3A)
)

val darkColors = AppColors(
    background = Color(0xFF2A2118),
    cardBackground = Color(0xFF3A3025),
    selectedCard = Color(0xFF4A3C2A),
    primaryText = Color(0xFFE8D9C5),
    secondaryText = Color(0xFFB8A690),
    accent = Color(0xFFD4A96A),
    button = Color(0xFF5C4B3A),
    buttonText = Color(0xFFF0E6D8)
)