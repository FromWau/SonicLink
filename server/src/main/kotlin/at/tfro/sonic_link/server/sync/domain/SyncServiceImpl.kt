package at.tfro.sonic_link.server.sync.domain

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.server.sync.domain.repository.GlobalChangeLogRepository
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import at.tfro.sonic_link.shared_rpc.sync.model.SyncRequest
import at.tfro.sonic_link.shared_rpc.sync.model.SyncResponse
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc
import kotlinx.coroutines.flow.Flow

class SyncServiceImpl(
    private val repo: GlobalChangeLogRepository,
    private val logger: Logger,
) : SyncService {
    companion object {
        const val LOG_TAG = "SyncService"
    }

    override fun subscribeToCurrentVersion(): Flow<SyncVersionRpc?> {
        logger.tag(LOG_TAG).i { "Subscribing to current sync version" }
        TODO()
    }

    override suspend fun sync(request: SyncRequest): SyncResponse {
        logger.tag(LOG_TAG).i { "Sync request received, from: ${request.currentVersion.version} -> ${request.targetVersion.version}" }
        TODO()
    }


    override suspend fun update(): SyncVersionRpc {
        logger.tag(LOG_TAG).i { "Sync update triggered" }
        TODO()
    }
}