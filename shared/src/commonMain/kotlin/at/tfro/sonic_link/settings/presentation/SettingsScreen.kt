package at.tfro.sonic_link.settings.presentation

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.app.Route
import at.tfro.sonic_link.core.domain.model.Setting
import at.tfro.sonic_link.core.presentation.side_drawer.SideDrawer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>(),
    onBack: () -> Unit,
    onNav: (Route) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SettingsAction.OnBack -> onBack()
                else -> Unit
            }

            viewModel.onAction(action)
        },
        onNav = onNav,
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
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
            modifier = Modifier.fillMaxSize().padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
            )

            Text("Select/Add/Remove servers too connect to.")

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    state.settings,
                    key = { it.id },
                ) { serverSetting ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        positionalThreshold = { it * .5f },
                    )

                    LaunchedEffect(dismissState.currentValue) {
                        if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                            onAction(SettingsAction.OnRemoveServer(serverSetting))
                        }
                    }

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromEndToStart = false,
                        backgroundContent = {
                            DismissBackground(dismissState)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        SettingsCard(
                            serverSetting = serverSetting,
                            onAction = onAction,
                        )
                    }
                }
            }

            var showAddServerDialog by remember { mutableStateOf(false) }
            OutlinedButton(
                onClick = { showAddServerDialog = true }
            ) {
                Text("Add Server")
            }

            if (showAddServerDialog) {
                AddSettingsCard(
                    onAction = onAction,
                    onDismiss = { showAddServerDialog = false }
                )
            }
        }
    }
}

@Composable
private fun SettingsCard(
    serverSetting: Setting,
    onAction: (SettingsAction) -> Unit,
) {
    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            var currentHost by remember { mutableStateOf(serverSetting.host) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                RadioButton(
                    selected = serverSetting.isActive,
                    onClick = {
                        onAction(
                            if (!serverSetting.isActive) {
                                SettingsAction.OnSelectServer(serverSetting)
                            } else {
                                SettingsAction.OnDeselectServer(serverSetting)
                            }
                        )
                    },
                )

                TextButton(
                    onClick = {
                        onAction(
                            SettingsAction.OnUpdateServer(
                                serverSetting.copy(host = currentHost)
                            )
                        )
                    },
                    enabled = currentHost != serverSetting.host
                ) {
                    Text("Update")
                }
            }

            TextField(
                value = currentHost,
                onValueChange = { currentHost = it },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Enter the URL of the server you want to connect to.") },
                label = { Text("URL") },
            )
        }
    }
}

@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        val color by animateColorAsState(
            when (dismissState.targetValue) {
                SwipeToDismissBoxValue.Settled -> Color.Transparent
                SwipeToDismissBoxValue.StartToEnd -> Color.Red
                SwipeToDismissBoxValue.EndToStart -> Color.Green
            }
        )

        Box(Modifier.fillMaxSize().clip(CardDefaults.shape).background(color))
    }
}

@Composable
private fun AddSettingsCard(
    onAction: (SettingsAction) -> Unit,
    onDismiss: () -> Unit,
) {
    var newServerUrl by remember { mutableStateOf("") }

    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Add New Server")

            TextField(
                value = newServerUrl,
                onValueChange = { newServerUrl = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Server URL") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAction(
                            SettingsAction.OnAddServer(newServerUrl)
                        )
                        onDismiss()
                    }
                ),
                supportingText = {
                    Text("Enter the URL of the server you want to add.")
                }
            )

            OutlinedButton(
                onClick = {
                    onAction(
                        SettingsAction.OnAddServer(newServerUrl)
                    )
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = newServerUrl.isNotBlank(),
            ) {
                Text("Add Server")
            }
        }
    }
}