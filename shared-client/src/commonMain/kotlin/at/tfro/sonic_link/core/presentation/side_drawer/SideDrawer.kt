package at.tfro.sonic_link.core.presentation.side_drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.tfro.sonic_link.app.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SideDrawer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    onNav: (Route) -> Unit,
    allowedDestinations: List<DrawerDestination> = DrawerDestination.entries,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Sonic Link",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(16.dp),
                    )

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
                }

                allowedDestinations.forEach { destination ->
                    TextButton(
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNav(destination.route)
                        },
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(destination.label.asString())
                    }
                }
            }
        },
        content = content,
    )
}