package at.tfro.sonic_link

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.tfro.sonic_link.library.presentation.music_list.MusicListScreen
import at.tfro.sonic_link.library.presentation.music_list.MusicListState
import at.tfro.sonic_link.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun MusicListScreenPreview() {
    AppTheme(darkTheme = true, dynamicColor = false) {
        MusicListScreen(
            state = MusicListState(),
            onAction = {},
        )
    }
}
