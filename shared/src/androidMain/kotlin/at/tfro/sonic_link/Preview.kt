package at.tfro.sonic_link

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.tfro.sonic_link.library.presentation.LibraryScreen
import at.tfro.sonic_link.library.presentation.LibraryState
import at.tfro.sonic_link.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun MusicListScreenPreview() {
    AppTheme(darkTheme = true, dynamicColor = false) {
        LibraryScreen(
            state = LibraryState(),
            onAction = {},
            onNav = {}
        )
    }
}
