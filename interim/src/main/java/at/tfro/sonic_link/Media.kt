package at.tfro.sonic_link

import at.tfro.sonic_link.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Media(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val path: String,
    val mediaType: MediaType,
) {
    enum class MediaType { AUDIO, VIDEO, }
}