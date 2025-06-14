package at.tfro.sonic_link

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.tfro.sonic_link.core.domain.model.Setting
import at.tfro.sonic_link.home.presentation.HomeScreen
import at.tfro.sonic_link.home.presentation.HomeState
import at.tfro.sonic_link.importer.presentation.ImporterScreen
import at.tfro.sonic_link.importer.presentation.ImporterState
import at.tfro.sonic_link.library.presentation.LibraryScreen
import at.tfro.sonic_link.library.presentation.LibraryState
import at.tfro.sonic_link.settings.presentation.SettingsScreen
import at.tfro.sonic_link.settings.presentation.SettingsState
import at.tfro.sonic_link.theme.AppTheme

@Composable
private fun PreviewContainer(
    content: @Composable () -> Unit,
) {
    AppTheme(darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                contentAlignment = Alignment.Center,
            ) {
                content.invoke()
            }
        }
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Home_Preview() {
    PreviewContainer {
        val state = HomeState()

        HomeScreen(
            state,
            onAction = {},
            onNav = {}
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Importer_Preview() {
    PreviewContainer {
        val state = ImporterState()

        ImporterScreen(
            state = state,
            onAction = {},
            onNav = {}
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Library_Preview() {
    PreviewContainer {
        val state = LibraryState()

        LibraryScreen(
            state,
            onAction = {},
            onNav = {}
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Settings_Preview_empty() {
    PreviewContainer {
        val state = SettingsState()

        SettingsScreen(
            state = state,
            onAction = {},
            onNav = {},
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Settings_Preview_not_empty() {
    PreviewContainer {
        val state = SettingsState(
            settings = listOf(
                Setting(
                    id = 0,
                    host = "example1.com",
                    isActive = false,
                ),
                Setting(
                    id = 1,
                    host = "example2.com",
                    isActive = true,
                ),
            )
        )

        SettingsScreen(
            state = state,
            onAction = {},
            onNav = {},
        )
    }
}
