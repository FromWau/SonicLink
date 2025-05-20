package at.tfro.sonic_link.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import at.tfro.sonic_link.db.dbo.MediaDbo
import java.util.UUID

@Dao
interface MediaDao {
    @Upsert
    suspend fun upsert(media: MediaDbo): Unit

    @Query("SELECT * FROM media WHERE id = :id")
    suspend fun getMediaById(id: UUID): MediaDbo?

    @Query("SELECT * FROM media")
    suspend fun getAllMedia(): List<MediaDbo>
}