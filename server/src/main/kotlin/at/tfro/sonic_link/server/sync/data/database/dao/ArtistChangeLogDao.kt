package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.tfro.sonic_link.server.sync.data.model.ArtistChangeLogEntity

@Dao
interface ArtistChangeLogDao {
    @Insert
    suspend fun insert(changeLog: ArtistChangeLogEntity): Long

    @Query("SELECT * FROM artist_change_log WHERE artist_id = :artistId")
    suspend fun getChangeLogsForArtist(artistId: String): List<ArtistChangeLogEntity>
}