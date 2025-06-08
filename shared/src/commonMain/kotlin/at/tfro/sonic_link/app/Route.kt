package at.tfro.sonic_link.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Graph : Route

    @Serializable
    data object Importer : Route

    @Serializable
    data object MusicList : Route
}