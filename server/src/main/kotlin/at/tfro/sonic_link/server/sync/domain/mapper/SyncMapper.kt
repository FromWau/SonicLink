package at.tfro.sonic_link.server.sync.domain.mapper

import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc

fun SyncVersion.toRpc(): SyncVersionRpc =
    SyncVersionRpc(
        version = this.version,
        updatedAt = this.updatedAt,
    )
