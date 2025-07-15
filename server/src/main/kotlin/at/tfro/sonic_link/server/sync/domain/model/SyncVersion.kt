package at.tfro.sonic_link.server.sync.domain.model

import kotlinx.datetime.LocalDateTime

class SyncVersion(
    val version: Long,
    val updatedAt: LocalDateTime
)