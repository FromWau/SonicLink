package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.tfro.sonic_link.server.sync.data.model.RecordChangeLogEntity

@Dao
interface RecordChangeLogDao {
    @Insert
    suspend fun insert(changeLog: RecordChangeLogEntity): Long

    @Query("SELECT * FROM record_change_log WHERE record_id = :recordId")
    suspend fun getChangeLogsForRecord(recordId: String): List<RecordChangeLogEntity>
}