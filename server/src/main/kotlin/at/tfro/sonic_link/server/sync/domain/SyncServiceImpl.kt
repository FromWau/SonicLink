package at.tfro.sonic_link.server.sync.domain

import at.tfro.sonic_link.core.logger.Log
import at.tfro.sonic_link.server.sync.domain.mapper.toDomain
import at.tfro.sonic_link.server.sync.domain.mapper.toRpc
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import at.tfro.sonic_link.shared_rpc.sync.model.SyncRequest
import at.tfro.sonic_link.shared_rpc.sync.model.SyncResponse
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SyncServiceImpl(
    private val repo: SyncRepository,
) : SyncService {
    companion object {
        const val TAG = "SyncService"
    }

    override fun subscribeToCurrentVersion(): Flow<SyncVersionRpc?> {
        Log.tag(TAG).i { "Subscribing to current sync version" }
        return repo.getCurrentSyncVersionFlow().map { it?.toRpc() }
    }

    override suspend fun sync(request: SyncRequest): SyncResponse {
        Log.tag(TAG)
            .i { "Sync request received, from: ${request.currentVersion.version} -> ${request.targetVersion.version}" }

        val delta = repo.generateDelta(
            currentVersion = request.currentVersion.toDomain(),
            targetVersion = request.targetVersion.toDomain()
        )
        TODO()
    }


    override suspend fun update(): SyncVersionRpc {
        Log.tag(TAG).i { "Sync update triggered" }
        return repo.versionBump().toRpc()
    }
}