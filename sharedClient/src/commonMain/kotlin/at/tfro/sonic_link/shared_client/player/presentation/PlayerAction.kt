package at.tfro.sonic_link.shared_client.player.presentation

sealed interface PlayerAction {
    data object IndexMedia : PlayerAction

    data object TogglePlay : PlayerAction
    data class SeekTo(val position: Long) : PlayerAction
    data class SetVolume(val volume: Float) : PlayerAction
    data object SkipNext : PlayerAction
    data object SkipPrevious : PlayerAction
    data object Random: PlayerAction
}