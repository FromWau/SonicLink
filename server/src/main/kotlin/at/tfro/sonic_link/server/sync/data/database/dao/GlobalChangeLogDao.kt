package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalChangeLogDao {
    @Query(
        """
        SELECT * FROM global_change_log
        WHERE version = (
            SELECT MAX(version) FROM global_change_log
        )
        ORDER BY timestamp DESC
    """
    )
    suspend fun getLatestVersion(): GlobalChangeLogEntity?

    @Query(
        """
        SELECT * FROM global_change_log
        WHERE version = (
            SELECT MAX(version) FROM global_change_log
        )
        ORDER BY timestamp DESC
    """
    )
    fun getLatestVersionFlow(): Flow<GlobalChangeLogEntity?>

    @Query("SELECT * FROM global_change_log ORDER BY version ASC")
    suspend fun getAllVersions(): List<GlobalChangeLogEntity>

    @Insert
    suspend fun insert(entry: GlobalChangeLogEntity)
}
