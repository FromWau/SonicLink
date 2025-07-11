package at.tfro.sonic_link.interim

import at.tfro.sonic_link.interim.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Artist(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid,
    val name: String,
    val path: String,
)