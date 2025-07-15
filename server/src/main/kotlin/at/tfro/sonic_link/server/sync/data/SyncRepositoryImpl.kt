package at.tfro.sonic_link.server.sync.data

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.server.sync.data.database.SyncDao
import at.tfro.sonic_link.server.sync.data.database.SyncVersionEntity
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SyncRepositoryImpl(
    private val dao: SyncDao,
    private val logger: Logger,
) : SyncRepository {
    override suspend fun getCurrentSyncVersion(): SyncVersion? =
        dao.getCurrentSyncVersion()?.toDomain()

    override fun getCurrentSyncVersionFlow() =
        dao.getCurrentSyncVersionFlow().map { it.toDomain() }

    // Fake update for testing the db and client connection
    override suspend fun update(): SyncVersion =
        dao.insertGetVersion(
            SyncVersionEntity(
                updatedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            )
        )
            .also { logger.tag("SyncRepository").i { "Inserted sync version: $it" } }

            ?.toDomain()
            ?: throw IllegalStateException("Failed to insert sync version")
}