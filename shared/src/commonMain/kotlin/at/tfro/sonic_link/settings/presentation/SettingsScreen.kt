package at.tfro.sonic_link.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.app.Route
import at.tfro.sonic_link.core.presentation.components.SideDrawer
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
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
            )

            state.serverSettings.let { serverSettings ->
                Card {
                    var currentBaseUrl by remember { mutableStateOf(serverSettings.baseUrl) }

                    Text("Base URL:")
                    TextField(
                        value = currentBaseUrl,
                        onValueChange = { currentBaseUrl = it },
                        modifier = Modifier.fillMaxSize(),
                    )

                    TextButton(
                        onClick = {
                            onAction(SettingsAction.OnServerSettingsChanged(currentBaseUrl))
                        },
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            "Update Base URL"
                        )
                    }
                }
            }
        }
    }
}