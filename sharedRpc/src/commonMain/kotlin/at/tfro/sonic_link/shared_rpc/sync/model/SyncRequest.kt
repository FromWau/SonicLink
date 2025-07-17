package at.tfro.sonic_link.shared_rpc.sync.model

import kotlinx.serialization.Serializable

@Serializable
data class SyncRequest(val currentVersion: SyncVersionRpc, val targetVersion: SyncVersionRpc)