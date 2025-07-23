package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

@Dao
interface SyncVersionDao {
    @Query(
        """
        SELECT * FROM sync_versions
        ORDER BY version DESC
        LIMIT 1
    """
    )
    suspend fun getCurrentSyncVersion(): SyncVersionEntity?

    @Query(
        """
        SELECT * FROM sync_versions
        ORDER BY version DESC
        LIMIT 1
    """
    )
    fun getCurrentSyncVersionFlow(): Flow<SyncVersionEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(syncVersion: SyncVersionEntity)

    @Transaction
    suspend fun getNextVersion(): SyncVersionEntity {
        val current = getCurrentSyncVersion()
        val nextVersion = (current?.version ?: 0L) + 1
        val newEntry = SyncVersionEntity(
            version = nextVersion,
            releasedAt = Clock.System.now(),
        )
        insert(newEntry)
        return newEntry
    }

    @Query("DELETE FROM sync_versions")
    suspend fun clear()

    @Query("SELECT * FROM sync_versions")
    suspend fun getAllSyncVersions(): List<SyncVersionEntity>

    @Query("SELECT * FROM sync_versions WHERE version = :version")
    suspend fun findByVersion(version: Long): SyncVersionEntity?
}