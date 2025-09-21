package at.tfro.sonic_link.shared_client.player.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun PlayerScreenRoot(
    viewModel: PlayerViewModel = koinViewModel<PlayerViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PlayerScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun PlayerScreen(
    state: PlayerState,
    onAction: (PlayerAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            IconButton(onClick = { onAction(PlayerAction.SkipPrevious) }) {
                Icon(
                    imageVector = Icons.Filled.SkipPrevious,
                    contentDescription = "Previous track",
                )
            }

            IconButton(
                onClick = { onAction(PlayerAction.TogglePlay) },
            ) {
                if (state.isPlaying) {
                    Icon(
                        imageVector = Icons.Filled.PauseCircleFilled,
                        contentDescription = "Pause",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.PlayCircleFilled,
                        contentDescription = "Play",
                    )
                }
            }

            IconButton(onClick = { onAction(PlayerAction.SkipNext) }) {
                Icon(
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = "Next track",
                )
            }
        }

        Row {
            TextButton(onClick = { onAction(PlayerAction.IndexMedia) }) {
                Text(text = "Index")
            }
        }
    }
}