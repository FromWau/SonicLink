package at.tfro.sonic_link.db.dao

import androidx.room.Dao
import androidx.room.Upsert
import at.tfro.sonic_link.db.dbo.AlbumDbo

@Dao
interface AlbumDao {
    @Upsert
    suspend fun upsert(album: AlbumDbo): Unit
}