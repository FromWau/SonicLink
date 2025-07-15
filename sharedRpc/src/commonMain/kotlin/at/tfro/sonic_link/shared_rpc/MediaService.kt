package at.tfro.sonic_link.shared_rpc

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

interface MediaService {
    suspend fun getMedia(id: Uuid): Record?
}

@Serializable
data class Record(
    val id: Uuid,
    val title: String,
    val album: Album,
    val artist: Artist,
    val path: String,
)

@Serializable
data class Album(
    val id: Uuid,
    val title: String,
    val artist: String,
    val coverArtPath: String?,
)

@Serializable
data class Artist(
    val id: Uuid,
    val name: String,
    val coverArtPath: String?,
)