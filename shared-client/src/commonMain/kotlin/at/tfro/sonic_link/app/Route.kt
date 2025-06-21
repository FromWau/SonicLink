package at.tfro.sonic_link.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Graph : Route

    @Serializable
    data object Home : Route

    sealed class Import : Route {
        @Serializable
        data object ImportList : Import()

        @Serializable
        data class ImportMedia(
            val path: String,
            val title: String,
            val artist: String,
            val album: String,
        ) : Import()
    }

    @Serializable
    data object Library : Route

    @Serializable
    data object Settings : Route
}