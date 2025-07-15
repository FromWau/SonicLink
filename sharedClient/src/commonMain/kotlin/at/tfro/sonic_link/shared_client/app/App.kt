package at.tfro.sonic_link.shared_client.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import at.tfro.sonic_link.shared_client.home.presentation.HomeScreenRoot
import at.tfro.sonic_link.shared_client.importer.presentation.import_media.ImportMediaScreenRoot
import at.tfro.sonic_link.shared_client.importer.presentation.importer_list.ImportListScreenRoot
import at.tfro.sonic_link.shared_client.library.presentation.LibraryScreenRoot
import at.tfro.sonic_link.shared_client.settings.presentation.SettingsScreenRoot
import at.tfro.sonic_link.shared_client.theme.AppTheme


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
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.Graph,
                ) {
                    navigation<Route.Graph>(
                        startDestination = Route.Home,
                    ) {
                        composable<Route.Home> {
                            HomeScreenRoot(
                                onBack = navController::navigateUp,
                                onNav = navController::navigate,
                            )
                        }

                        composable<Route.Library> {
                            LibraryScreenRoot(
                                onBack = navController::navigateUp,
                                onNav = navController::navigate,
                            )
                        }

                        composable<Route.Import.ImportList> {
                            ImportListScreenRoot(
                                onBack = navController::navigateUp,
                                onNav = navController::navigate,
                            )
                        }

                        composable<Route.Import.ImportMedia> {
                            ImportMediaScreenRoot(
                                onBack = navController::navigateUp,
                                onNav = navController::navigate,
                            )
                        }

                        composable<Route.Settings> {
                            SettingsScreenRoot(
                                onBack = navController::navigateUp,
                                onNav = navController::navigate,
                            )
                        }
                    }
                }
            }
        }
    }
}
