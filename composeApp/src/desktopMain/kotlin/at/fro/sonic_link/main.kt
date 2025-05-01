package at.fro.sonic_link

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SonicLink",
    ) {
        App()
    }
}