package at.tfro.sonic_link.library.presentation

import at.tfro.sonic_link.library.domain.Music

sealed interface LibraryAction {
    data object OnBack : LibraryAction
    data class OnMusicClick(val music: Music) : LibraryAction
}