package at.tfro.sonic_link.app

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
import at.tfro.sonic_link.home.presentation.HomeScreenRoot
import at.tfro.sonic_link.importer.presentation.ImporterScreenRoot
import at.tfro.sonic_link.library.presentation.LibraryScreenRoot
import at.tfro.sonic_link.settings.presentation.SettingsScreenRoot
import at.tfro.sonic_link.theme.AppTheme


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
                        startDestination = Route.Importer,
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

                        composable<Route.Importer> {
                            ImporterScreenRoot(
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