package at.tfro.sonic_link

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import at.tfro.sonic_link.di.initKoin

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