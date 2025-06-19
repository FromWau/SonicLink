package at.tfro.sonic_link.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme = when {
    // TODO: Implement dynamic color for native
    darkTheme -> darkScheme
    else -> lightScheme
}