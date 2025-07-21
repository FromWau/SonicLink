package at.tfro.sonic_link.server.sync.domain.model

import kotlinx.datetime.LocalDateTime

data class SyncVersion(
    val version: Long,
    val releasedAt: LocalDateTime,
) {
    fun toPrettyString(preSpacing: String = ""): String {
        return "SyncVersion {\n" +
                "$preSpacing  version=$version,\n" +
                "$preSpacing  releasedAt=$releasedAt\n" +
                "$preSpacing}"
    }
}