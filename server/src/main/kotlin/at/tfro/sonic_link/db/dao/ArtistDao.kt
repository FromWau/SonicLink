package at.tfro.sonic_link.db.dao

import androidx.room.Dao
import androidx.room.Upsert
import at.tfro.sonic_link.db.dbo.ArtistDbo

@Dao
interface ArtistDao {
    @Upsert
    suspend fun upsert(artist: ArtistDbo): Unit
}