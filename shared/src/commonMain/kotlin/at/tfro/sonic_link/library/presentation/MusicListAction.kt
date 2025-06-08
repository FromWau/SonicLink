package at.tfro.sonic_link.library.presentation

import at.tfro.sonic_link.library.domain.Music

sealed interface MusicListAction {
    data object OnBack : MusicListAction
    data class OnMusicClick(val music: Music) : MusicListAction
}