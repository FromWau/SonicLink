package at.tfro.sonic_link

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import at.tfro.sonic_link.library.presentation.music_list.MusicListScreenRoot
import at.tfro.sonic_link.library.presentation.music_list.MusicListViewModel
import at.tfro.sonic_link.theme.AppTheme


@Composable
fun App() {
    AppTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize().statusBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                MusicListScreenRoot(
                    onBack = { /* TODO */ },
                    viewModel = MusicListViewModel(),
                )
            }
        }
    }
}