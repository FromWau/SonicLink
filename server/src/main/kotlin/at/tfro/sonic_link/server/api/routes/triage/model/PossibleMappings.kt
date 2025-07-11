package at.tfro.sonic_link.server.api.routes.triage.model

import kotlinx.serialization.Serializable


@Serializable
data class PossibleMappings(
    val media: Media,
    val mappings: List<Recording>,
)

@Serializable
data class Recording(
    val id: String,
    val score: Int,
    val artistCreditId: String,
    val title: String,
    val disambiguation: String? = null,
    val video: String? = null,
    val artistCredit: List<ArtistCredit>,
)

@Serializable
data class ArtistCredit(
    val name: String,
    val artist: Artist,
)

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val sortName: String? = null,
    val disambiguation: String? = null,
)
