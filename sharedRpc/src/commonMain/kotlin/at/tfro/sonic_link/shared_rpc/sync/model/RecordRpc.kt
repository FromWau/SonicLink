package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class RecordRpc(
    val id: Uuid,
    val title: String,
    val album: AlbumRpc,
    val artist: ArtistRpc,
    val path: String,
)

@Serializable
@Polymorphic
sealed class RecordOperation {
    @Serializable
    data class Delete(val id: Uuid) : RecordOperation()

    @Serializable
    data class Add(val record: AddRecord) : RecordOperation() {
        @Serializable
        data class AddRecord(
            val title: String,
            val album: AlbumRpc,
            val artist: ArtistRpc,
            val path: String,
        )
    }

    @Serializable
    data class Update(val record: UpdateRecord) : RecordOperation() {
        @Serializable
        data class UpdateRecord(
            val id: Uuid,
            val title: String? = null,
            val album: AlbumRpc? = null,
            val artist: ArtistRpc? = null,
            val path: String? = null,
        )
    }
}
