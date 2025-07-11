package at.tfro.sonic_link.server.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import at.tfro.sonic_link.server.db.dbo.ArtistDbo

@Dao
interface ArtistDao {
    // INFO: DO NOT USE @Upsert, this breaks desktop platform
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(artist: ArtistDbo)
}