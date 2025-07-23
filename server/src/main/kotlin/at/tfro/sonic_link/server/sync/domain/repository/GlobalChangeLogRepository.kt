package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.GlobalChangeLog
import kotlinx.coroutines.flow.Flow


interface GlobalChangeLogRepository {
    suspend fun getLatestVersion(): GlobalChangeLog?
    fun getCurrentSyncVersionFlow(): Flow<GlobalChangeLog?>

    suspend fun getAllVersions(): List<GlobalChangeLog>
}