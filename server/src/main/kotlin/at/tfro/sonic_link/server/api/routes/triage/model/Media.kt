package at.tfro.sonic_link.server.api.routes.triage.model

import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val path: String,
    val title: String,
    val album: String,
    val artist: String,
)