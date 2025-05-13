package at.tfro.sonic_link.library.presentation.music_list

import at.tfro.sonic_link.library.domain.Music

sealed interface MusicListAction {
    data class OnMusicClick(val music: Music) : MusicListAction
}