package at.tfro.sonic_link.server.sync.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncDao {
    @Query("""
        SELECT * FROM sync_version 
        ORDER BY id DESC
        LIMIT 1
    """)
    suspend fun getCurrentSyncVersion(): SyncVersionEntity?

    @Query("""
        SELECT * FROM sync_version 
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
}