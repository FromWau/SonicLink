package at.tfro.sonic_link.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object UuidSerializer : KSerializer<Uuid> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    @OptIn(ExperimentalUuidApi::class)
    override fun deserialize(decoder: Decoder): Uuid =
        Uuid.parse(decoder.decodeString())

    @OptIn(ExperimentalUuidApi::class)
    override fun serialize(encoder: Encoder, value: Uuid): Unit =
        encoder.encodeString(value.toString())
}
