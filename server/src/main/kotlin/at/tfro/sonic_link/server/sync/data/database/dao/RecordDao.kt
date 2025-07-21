package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.RecordWithRelations

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: RecordEntity)

    @Update
    suspend fun update(record: RecordEntity)

    @Delete
    suspend fun delete(record: RecordEntity)

    @Query("SELECT * FROM records")
    suspend fun getAll(): List<RecordEntity>

    @Transaction
    @Query(
        """
        SELECT 
            r.id AS record_id,
            r.title AS record_title,
            r.path AS record_path,
            r.sync_version_version AS record_sync_version_version,
            rsv.released_at AS record_sync_version_released_at,
            r.last_modified AS record_last_modified,
            r.is_deleted AS record_is_deleted,

            al.id AS album_id,
            al.title AS album_title,
            al.cover_art_path AS album_cover_art_path,
            al.artist_id AS album_artist_id,
            al.path AS album_path,
            al.sync_version_version AS album_sync_version_version,
            alsv.released_at AS album_sync_version_released_at,
            al.last_modified AS album_last_modified,
            al.is_deleted AS album_is_deleted,
            
            alar.name AS album_artist_name,
            alar.cover_art_path AS album_artist_cover_art_path,
            alar.path AS album_artist_path,
            alar.sync_version_version AS album_artist_sync_version_version,
            alarsv.released_at AS album_artist_sync_version_released_at,
            alar.last_modified AS album_artist_last_modified,
            alar.is_deleted AS album_artist_is_deleted,

            ar.id AS artist_id,
            ar.name AS artist_name,
            ar.cover_art_path AS artist_cover_art_path,
            ar.path AS artist_path,
            ar.sync_version_version AS artist_sync_version_version,
            arsv.released_at AS artist_sync_version_released_at,
            ar.last_modified AS artist_last_modified,
            ar.is_deleted AS artist_is_deleted

        FROM records r
        JOIN sync_versions rsv ON r.sync_version_version = rsv.version
        
        JOIN albums al ON r.album_id = al.id
        JOIN sync_versions alsv ON al.sync_version_version = alsv.version
        
        JOIN artists ar ON r.artist_id = ar.id
        JOIN sync_versions arsv ON ar.sync_version_version = arsv.version
        
        JOIN artists alar ON al.artist_id = alar.id
        JOIN sync_versions alarsv ON alar.sync_version_version = alarsv.version
    """
    )
    suspend fun getAllRecordsWithAlbumAndArtist(): List<RecordWithRelations>

    @Query("DELETE FROM records")
    suspend fun clear()
}