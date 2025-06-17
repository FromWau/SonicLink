package at.tfro.sonic_link.api.routes.triage.model

import at.tfro.sonic_link.features.musicbrainz_api.Recording
import kotlinx.serialization.Serializable


@Serializable
data class PossibleMappings(
    val media: Media,
    val mappings: List<Recording>,
)
