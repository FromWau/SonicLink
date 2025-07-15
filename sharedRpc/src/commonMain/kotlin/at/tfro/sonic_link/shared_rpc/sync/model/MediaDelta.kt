package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class SyncVersionRpc(
    val version: Long,
    val updatedAt: LocalDateTime,
)


@Serializable
data class EntityDelta<T>(
    val added: List<T>,
    val updated: List<T>,
    val deleted: List<Uuid>,
)


@Serializable
data class SyncDelta(
    val artists: EntityDelta<Artist>,
    val albums: EntityDelta<Album>,
    val media: EntityDelta<Record>,
)

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


@Serializable
data class SyncRequest(val clientVersion: SyncVersionRpc)

@Serializable
data class SyncResponse(val currentVersion: SyncVersionRpc, val delta: SyncDelta)
