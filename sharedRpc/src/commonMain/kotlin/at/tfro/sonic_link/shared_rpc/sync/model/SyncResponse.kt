package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.serialization.Serializable

@Serializable
data class SyncResponse(val currentVersion: SyncVersionRpc, val deltas: List<SyncDelta>) {

    @Serializable
    data class SyncDelta(
        val version: SyncVersionRpc,
        val records: List<RecordOperation>,
        val albums: List<AlbumOperation>,
        val artists: List<ArtistOperation>,
    )
}
