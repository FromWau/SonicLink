package at.tfro.sonic_link.shared_client.library.presentation

import at.tfro.sonic_link.shared_client.library.domain.Music

sealed interface LibraryAction {
    data object OnBack : LibraryAction
    data class OnMusicClick(val music: Music) : LibraryAction
}