package at.tfro.sonic_link.shared_client.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.shared_client.app.Route
import at.tfro.sonic_link.shared_client.core.presentation.side_drawer.SideDrawer
import at.tfro.sonic_link.shared_client.core.presentation.side_drawer.toggle
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc
import io.ktor.client.HttpClient
import io.ktor.http.encodedPath
import kotlinx.coroutines.launch
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import org.koin.compose.viewmodel.koinViewModel


val client by lazy {
    HttpClient {
        installKrpc()
    }
}

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    onBack: () -> Unit,
    onNav: (Route) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnBack -> onBack()
                else -> Unit
            }

            viewModel.onAction(action)
        },
        onNav = onNav,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNav: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.toggle()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                        )
                    }
                },
            )


            var serviceOrNull: SyncService? by remember { mutableStateOf(null) }

            LaunchedEffect(Unit) {
                serviceOrNull = client.rpc {
                    url {
                        host = "localhost"
                        port = 8080
                        encodedPath = "/sync/update"
                    }

                    rpcConfig {
                        serialization {
                            json()
                        }
                    }
                }.withService()
            }

            val service = serviceOrNull // for smart casting

            val receivedVersions = remember { mutableListOf<SyncVersionRpc>() }
            if (service != null) {
                val coroutineScope = rememberCoroutineScope()

                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            service.update()
                        }
                    },
                ) {
                    Text("Send Update Request")
                }

                LaunchedEffect(service) {
                    service.subscribeToCurrentVersion().collect {
                        receivedVersions.add(it)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(receivedVersions) { item ->
                    Text(
                        text = "Received Version: ${item.version} at ${item.updatedAt}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}