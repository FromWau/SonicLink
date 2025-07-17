package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SyncVersionRpc(
    val version: Long,
    val updatedAt: LocalDateTime,
)