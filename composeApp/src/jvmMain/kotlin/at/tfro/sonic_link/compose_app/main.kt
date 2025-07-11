package at.tfro.sonic_link.compose_app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import at.tfro.sonic_link.shared_client.app.App
import at.tfro.sonic_link.shared_client.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SonicLink",
        ) {
            App()
        }
    }
}