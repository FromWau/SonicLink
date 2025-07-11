package at.tfro.sonic_link.interim

import at.tfro.sonic_link.interim.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Media(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid,
    val name: String,
    val path: String,
    val mediaType: MediaType,
) {
    enum class MediaType { AUDIO, VIDEO, }
}