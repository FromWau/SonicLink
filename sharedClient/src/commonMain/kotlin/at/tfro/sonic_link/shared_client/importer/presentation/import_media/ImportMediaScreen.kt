package at.tfro.sonic_link.shared_client.importer.presentation.import_media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.shared_client.app.Route
import at.tfro.sonic_link.shared_client.core.presentation.side_drawer.SideDrawer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImportMediaScreenRoot(
    viewModel: ImportMediaViewModel = koinViewModel<ImportMediaViewModel>(),
    onBack: () -> Unit,
    onNav: (Route) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ImportMediaScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ImportMediaAction.OnBack -> onBack()
            }

            viewModel.onAction(action)
        },
        onNav = onNav,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportMediaScreen(
    state: ImportMediaState,
    onAction: (ImportMediaAction) -> Unit,
    onNav: (Route) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideDrawer(
        drawerState = drawerState,
        scope = scope,
        onNav = onNav,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Importer",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(ImportMediaAction.OnBack) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    state.hits,
                ) {
                    ImportMediaListItem(
                        media = it,
                        onAction = onAction,
                    )
                }
            }
        }
    }
}

@Composable
fun ImportMediaListItem(
    media: Recording,
    onAction: (ImportMediaAction) -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = media.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = media.artistCredit.joinToString { it.name },
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (media.disambiguation != null) {
                    Text(
                        text = media.disambiguation,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}