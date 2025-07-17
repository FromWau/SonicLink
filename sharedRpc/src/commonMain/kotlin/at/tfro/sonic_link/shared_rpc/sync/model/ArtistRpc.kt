package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class ArtistRpc(
    val id: Uuid,
    val name: String,
    val coverArtPath: String?,
)

@Serializable
@Polymorphic
sealed class ArtistOperation {
    @Serializable
    data class Delete(val id: Uuid) : ArtistOperation()

    @Serializable
    data class Add(val artist: AddArtist) : ArtistOperation() {
        @Serializable
        data class AddArtist(
            val name: String,
            val coverArtPath: String?,
        )
    }

    @Serializable
    data class Update(val artist: UpdateArtist) : ArtistOperation() {
        @Serializable
        data class UpdateArtist(
            val id: Uuid,
            val name: String? = null,
            val coverArtPath: String? = null,
        )
    }
}