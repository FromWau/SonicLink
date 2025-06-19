package at.tfro.sonic_link

import at.tfro.sonic_link.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class Media @OptIn(ExperimentalUuidApi::class) constructor(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid,
    val name: String,
    val path: String,
    val mediaType: MediaType,
) {
    enum class MediaType { AUDIO, VIDEO, }
}