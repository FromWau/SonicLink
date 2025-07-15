package at.tfro.sonic_link.server.sync.data.mapper

import at.tfro.sonic_link.server.sync.data.database.SyncVersionEntity
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion

fun SyncVersion.toSyncVersionEntity(): SyncVersionEntity =
    SyncVersionEntity(
        updatedAt = this.updatedAt,
    )

fun SyncVersionEntity.toDomain(): SyncVersion =
    SyncVersion(
        version = this.id,
        updatedAt = this.updatedAt,
    )
