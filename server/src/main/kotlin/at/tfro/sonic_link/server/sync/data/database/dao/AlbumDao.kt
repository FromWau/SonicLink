package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import at.tfro.sonic_link.server.sync.data.model.AlbumEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumWithRelations
import kotlin.uuid.Uuid

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(album: AlbumEntity)

    @Query("SELECT * FROM albums WHERE path = :path")
    suspend fun getByPath(path: String): AlbumEntity?

    @Update
    suspend fun update(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)

    @Transaction
    @Query("SELECT * FROM albums")
    suspend fun getAll(): List<AlbumWithRelations>

    @Query("DELETE FROM albums")
    suspend fun clear()
}