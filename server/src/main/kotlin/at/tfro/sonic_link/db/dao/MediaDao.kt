package at.tfro.sonic_link.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import at.tfro.sonic_link.db.dbo.MediaDbo
import java.util.UUID

@Dao
interface MediaDao {
    // INFO: DO NOT USE @Upsert, this breaks desktop platform
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(media: MediaDbo)

    @Query("SELECT * FROM media WHERE id = :id")
    suspend fun getMediaById(id: UUID): MediaDbo?

    @Query("SELECT * FROM media")
    suspend fun getAllMedia(): List<MediaDbo>
}