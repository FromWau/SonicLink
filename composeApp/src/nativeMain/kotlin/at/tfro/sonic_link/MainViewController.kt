package at.tfro.sonic_link

import androidx.compose.ui.window.ComposeUIViewController
import at.tfro.sonic_link.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }