package at.tfro.sonic_link.core.presentation.side_drawer

import androidx.compose.material3.DrawerState

suspend fun DrawerState.toggle() {
    if (isClosed) {
        this.open()
    } else {
        this.close()
    }
}