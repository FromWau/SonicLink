package at.tfro.sonic_link.library.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.Permission
import at.tfro.sonic_link.RequestPermission
import at.tfro.sonic_link.library.presentation.components.MusicList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MusicListScreenRoot(
    viewModel: MusicListViewModel = koinViewModel<MusicListViewModel>(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MusicListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is MusicListAction.OnBack -> onBack()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}


@Composable
fun MusicListScreen(
    state: MusicListState,
    onAction: (MusicListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val musicListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        musicListState.scrollToItem(0)
    }

    RequestPermission(
        permission = Permission.NETWORK,
    ) {
        println("Requesting permission")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Music List",
            style = MaterialTheme.typography.headlineLarge,
        )

        MusicList(
            musics = state.musicList,
            onMusicClick = {
                onAction(MusicListAction.OnMusicClick(it))
            },
            scrollState = musicListState,
        )
    }
}