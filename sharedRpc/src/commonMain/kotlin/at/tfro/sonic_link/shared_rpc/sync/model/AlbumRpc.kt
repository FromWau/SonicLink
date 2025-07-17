package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class AlbumRpc(
    val id: Uuid,
    val title: String,
    val artist: String,
    val coverArtPath: String?,
)

@Serializable
@Polymorphic
sealed class AlbumOperation {
    @Serializable
    data class Delete(val id: Uuid) : AlbumOperation()

    @Serializable
    data class Add(val album: AddAlbum) : AlbumOperation() {
        @Serializable
        data class AddAlbum(
            val title: String,
            val artist: String,
            val coverArtPath: String?,
        )
    }

    @Serializable
    data class Update(val album: UpdateAlbum) : AlbumOperation() {
        @Serializable
        data class UpdateAlbum(
            val id: Uuid,
            val title: String? = null,
            val artist: String? = null,
            val coverArtPath: String? = null,
        )
    }
}
