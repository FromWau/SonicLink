package at.tfro.sonic_link.importer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImporterScreenRoot(
    viewModel: ImporterViewModel = koinViewModel<ImporterViewModel>(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ImporterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ImporterAction.OnBack -> onBack()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}

@Composable
fun ImporterScreen(
    state: ImporterState,
    onAction: (ImporterAction) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.mediaToImport) { media ->

                    Card {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Column {
                                Row {
                                    Text(
                                        text = media.path,
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }

                                HorizontalDivider()

                                var title by remember { mutableStateOf(media.title) }
                                var artist by remember { mutableStateOf(media.artist) }
                                var album by remember { mutableStateOf(media.album) }

                                Row {
                                    TextField(
                                        value = title,
                                        onValueChange = { title = it },
                                        label = { Text("Enter title") },
                                        modifier = Modifier.fillMaxWidth(),
                                    )

                                    TextField(
                                        value = artist,
                                        onValueChange = { artist = it },
                                        label = { Text("Enter artist") },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    TextField(
                                        value = album,
                                        onValueChange = { album = it },
                                        label = { Text("Enter album") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Row {
                                    TextButton(
                                        onClick = {
                                            onAction(
                                                ImporterAction.OnImportableMediaSelected(
                                                    media.copy(
                                                        title = title,
                                                        artist = artist,
                                                        album = album
                                                    )
                                                )
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Identify")
                                    }
                                }
                            }
                        }
                    }
                }

                items(state.identifiedMedia) { media ->
                    Card(
                        modifier = Modifier.background(Color.Green.copy(alpha = .8f))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Column {
                                Row {
                                    Text(
                                        text = media.path,
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }

                                HorizontalDivider()

                                Row {
                                    Text(text = "Title: ${media.title}")
                                    Text(text = "Artist: ${media.artist}")
                                    Text(text = "Album: ${media.album}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}