package at.tfro.sonic_link.shared_client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.tfro.sonic_link.shared_client.core.domain.model.Setting
import at.tfro.sonic_link.shared_client.home.presentation.HomeScreen
import at.tfro.sonic_link.shared_client.home.presentation.HomeState
import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia
import at.tfro.sonic_link.shared_client.importer.presentation.import_media.ImportMediaScreen
import at.tfro.sonic_link.shared_client.importer.presentation.import_media.ImportMediaState
import at.tfro.sonic_link.shared_client.importer.presentation.importer_list.ImportListScreen
import at.tfro.sonic_link.shared_client.importer.presentation.importer_list.ImportListState
import at.tfro.sonic_link.shared_client.library.presentation.LibraryScreen
import at.tfro.sonic_link.shared_client.library.presentation.LibraryState
import at.tfro.sonic_link.shared_client.settings.presentation.SettingsScreen
import at.tfro.sonic_link.shared_client.settings.presentation.SettingsState
import at.tfro.sonic_link.shared_client.theme.AppTheme
import kotlin.uuid.Uuid

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
private fun Importer_Preview_empty() {
    PreviewContainer {
        val state = ImportListState()

        ImportListScreen(
            state = state,
            onAction = {},
            onNav = {}
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun Importer_Preview_notEmpty() {
    PreviewContainer {
        val state = ImportListState(
            mediaToImport = listOf(
                ImportMedia(
                    path = "SystemOfADown/ChopSuey!.mp3",
                    title = "Chop Suey!",
                    album = "",
                    artist = "System Of ADown",
                ),
                ImportMedia(
                    path = "Gorillaz/FeelGoodInc..mp3",
                    title = "Feel Good Inc.",
                    album = "",
                    artist = "Gorillaz",
                )
            )
        )

        ImportListScreen(
            state = state,
            onAction = {},
            onNav = {}
        )
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
private fun ImporterMedia_Preview() {
    PreviewContainer {
        val state = ImportMediaState(
            media = ImportMedia(
                path = ".",
                title = "Title",
                album = "Album",
                artist = "Artist",
            )
        )

        ImportMediaScreen(
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
                    id = Uuid.random(),
                    host = "example1.com",
                    isActive = false,
                ),
                Setting(
                    id = Uuid.random(),
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
