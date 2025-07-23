package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.tfro.sonic_link.server.sync.data.model.AlbumChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumChangeLogWithAlbum
import kotlin.uuid.Uuid

@Dao
interface AlbumChangeLogDao {
    @Insert
    suspend fun insert(changeLog: AlbumChangeLogEntity): Long

    @Query("SELECT * FROM album_change_log WHERE album_id = :albumId")
    suspend fun getChangeLogsForAlbum(albumId: Uuid): List<AlbumChangeLogWithAlbum>
}