package at.tfro.sonic_link.server.sync.domain

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
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
    private val logger: Logger,
) : SyncService {
    override fun subscribeToCurrentVersion(): Flow<SyncVersionRpc> =
        repo.getCurrentSyncVersionFlow().map { it.toRpc() }

    override suspend fun sync(request: SyncRequest): SyncResponse =
        TODO("Not yet implemented")

    override suspend fun update(): SyncVersionRpc {
        logger.tag("SyncService").i { "Sync update triggered" }
        return repo.update().toRpc().also {
            logger.tag("SyncService").i { "Sync update completed: $it" }
        }
    }
}