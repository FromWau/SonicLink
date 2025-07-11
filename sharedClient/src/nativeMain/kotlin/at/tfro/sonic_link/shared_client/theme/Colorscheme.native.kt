package at.tfro.sonic_link.shared_client.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import at.tfro.sonic_link.shared_client.theme.darkScheme
import at.tfro.sonic_link.shared_client.theme.lightScheme

@Composable
actual fun getColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme = when {
    // TODO: Implement dynamic color for native
    darkTheme -> darkScheme
    else -> lightScheme
}