package at.tfro.sonic_link.shared_client.app

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
        data object ImportMedia : Import()
    }

    @Serializable
    data object Library : Route

    @Serializable
    data object Settings : Route
}