package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MNDColorScheme = darkColorScheme(
    primary = PrimaryCyan,
    onPrimary = DarkBackground,
    secondary = PrimaryBlue,
    onSecondary = TextWhite,
    tertiary = AccentPurple,
    onTertiary = TextWhite,
    background = DarkBackground,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextMuted,
    outline = GlassBorder
)

@Composable
fun MNDTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MNDColorScheme,
        typography = Typography,
        content = content
    )
}

