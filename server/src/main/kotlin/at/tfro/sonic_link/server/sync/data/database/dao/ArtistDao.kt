package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import at.tfro.sonic_link.server.sync.data.model.ArtistEntity
import kotlin.uuid.Uuid

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(artist: ArtistEntity)

    @Query("SELECT * FROM artists WHERE path = :path")
    suspend fun getByPath(path: String): ArtistEntity?

    @Update
    suspend fun update(artist: ArtistEntity)

    @Delete
    suspend fun delete(artist: ArtistEntity)

    @Query("SELECT * FROM artists")
    suspend fun getAll(): List<ArtistEntity>

    @Query("DELETE FROM artists")
    suspend fun clear()
}