package com.example.testapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE), // Primary color
    secondary = Color(0xFF03DAC5), // Secondary color
    background = Color.White, // Background color
    surface = Color.White, // Surface color (e.g., cards)
    error = Color(0xFFB00020), // Error color
    onPrimary = Color.White, // Text color on primary background
    onSecondary = Color.Black, // Text color on secondary background
    onBackground = Color.Black, // Text color on background
    onSurface = Color.Black, // Text color on surface
    onError = Color.White // Text color on error background
)
@Composable
fun NestedColumnTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette // Always use the light mode color palette

    MaterialTheme(
        colorScheme = colors, // Apply the color scheme
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}