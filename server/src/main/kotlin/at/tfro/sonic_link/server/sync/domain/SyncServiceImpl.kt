package at.tfro.sonic_link.server.sync.domain

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import at.tfro.sonic_link.shared_rpc.sync.model.SyncRequest
import at.tfro.sonic_link.shared_rpc.sync.model.SyncResponse
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

class SyncServiceImpl(
    private val repo: SyncRepository,
    private val logger: Logger,
) : SyncService {
    @OptIn(ExperimentalTime::class)
    override fun subscribeToCurrentVersion(): Flow<SyncVersionRpc> {
        return flow {
            repeat(10) {
                delay(300)
//                println("Emitting sync version update: $it")
                logger.tag("SyncService").i { "Emitting sync version update: $it" }
                emit(
                    SyncVersionRpc(
                        it.toLong(),
                        Clock.System.now().toLocalDateTime(TimeZone.UTC)
                    )
                )
            }
        }
    }
//        repo.getCurrentSyncVersionFlow().map { it.toRpc() }

    override suspend fun sync(request: SyncRequest): SyncResponse =
        TODO("Not yet implemented")

    @OptIn(ExperimentalTime::class)
    override suspend fun update(): SyncVersionRpc {
//        println("Sync update triggered")
        logger.tag("SyncService").i { "Sync update triggered" }
        return SyncVersionRpc(
            1L,
            Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
//        return repo.update().toRpc().also {
//            logger.tag("SyncService").i { "Sync update completed: $it" }
//        }
    }

    override fun subscribeToNews(): Flow<String> {
        return flow {
            repeat(10) {
                delay(300)
                logger.tag("UserService").i { "Emitting article number $it" }
                emit("Article number $it")
            }
        }
    }
}