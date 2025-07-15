package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import kotlinx.coroutines.flow.Flow

interface SyncRepository {
    suspend fun getCurrentSyncVersion(): SyncVersion?
    fun getCurrentSyncVersionFlow(): Flow<SyncVersion>

    suspend fun update(): SyncVersion
}
