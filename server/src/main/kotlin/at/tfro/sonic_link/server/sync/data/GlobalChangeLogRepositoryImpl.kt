package at.tfro.sonic_link.server.sync.data

import at.tfro.sonic_link.server.sync.data.database.dao.GlobalChangeLogDao
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.domain.model.GlobalChangeLog
import at.tfro.sonic_link.server.sync.domain.repository.GlobalChangeLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GlobalChangeLogRepositoryImpl(
    private val globalChangeLogDao: GlobalChangeLogDao,
) : GlobalChangeLogRepository {
    override suspend fun getLatestVersion(): GlobalChangeLog? =
        globalChangeLogDao.getLatestVersion()?.toDomain()

    override fun getCurrentSyncVersionFlow(): Flow<GlobalChangeLog?> =
        globalChangeLogDao.getLatestVersionFlow().map { it?.toDomain() }

    override suspend fun getAllVersions(): List<GlobalChangeLog> =
        globalChangeLogDao.getAllVersions().map { it.toDomain() }
}