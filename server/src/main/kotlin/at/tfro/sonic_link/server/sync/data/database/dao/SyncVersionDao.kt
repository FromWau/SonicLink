package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncVersionDao {
    @Query("""
        SELECT * FROM sync_versions
        ORDER BY id DESC
        LIMIT 1
    """)
    suspend fun getCurrentSyncVersion(): SyncVersionEntity?

    @Query("""
        SELECT * FROM sync_versions
        ORDER BY id DESC
        LIMIT 1
    """)
    fun getCurrentSyncVersionFlow(): Flow<SyncVersionEntity?>

    @Insert
    suspend fun insert(syncVersion: SyncVersionEntity)

    @Transaction
    suspend fun insertGetVersion(syncVersion: SyncVersionEntity): SyncVersionEntity? {
        insert(syncVersion)
        return getCurrentSyncVersion()
    }

    @Query("DELETE FROM sync_versions")
    suspend fun clear()
}