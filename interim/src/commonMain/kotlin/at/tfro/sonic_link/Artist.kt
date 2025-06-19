package at.tfro.sonic_link

import at.tfro.sonic_link.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class Artist @OptIn(ExperimentalUuidApi::class) constructor(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid,
    val name: String,
    val path: String,
)