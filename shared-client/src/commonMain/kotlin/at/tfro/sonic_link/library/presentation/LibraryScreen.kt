package at.tfro.sonic_link.library.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.Permission
import at.tfro.sonic_link.RequestPermission
import at.tfro.sonic_link.app.Route
import at.tfro.sonic_link.core.presentation.side_drawer.SideDrawer
import at.tfro.sonic_link.library.presentation.components.MusicList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LibraryScreenRoot(
    viewModel: LibraryViewModel = koinViewModel<LibraryViewModel>(),
    onBack: () -> Unit,
    onNav: (Route) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LibraryAction.OnBack -> onBack()
                else -> Unit
            }

            viewModel.onAction(action)
        },
        onNav = onNav,
    )
}


@Composable
fun LibraryScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit,
    onNav: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        scrollState.scrollToItem(0)
    }

    RequestPermission(
        permission = Permission.NETWORK,
    ) {
        println("Requesting permission")
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideDrawer(
        drawerState = drawerState,
        scope = scope,
        onNav = onNav,
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Library",
                style = MaterialTheme.typography.headlineLarge,
            )

            MusicList(
                musics = state.musicList,
                onMusicClick = {
                    onAction(LibraryAction.OnMusicClick(it))
                },
                scrollState = scrollState,
            )
        }
    }
}