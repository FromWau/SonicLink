package at.tfro.sonic_link.importer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PossibleMediaDto(
    val path: String,
    val possibleTitle: String,
    val possibleAlbum: String,
    val possibleArtist: String,
)
