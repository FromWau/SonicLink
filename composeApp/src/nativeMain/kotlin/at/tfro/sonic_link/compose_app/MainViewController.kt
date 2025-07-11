package at.tfro.sonic_link.compose_app

import androidx.compose.ui.window.ComposeUIViewController
import at.tfro.sonic_link.shared_client.app.App
import at.tfro.sonic_link.shared_client.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }