package at.tfro.sonic_link.server.sync.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.RecordWithAlbumAndArtistEntity

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

            al.id AS album_id,
            al.title AS album_title,
            al.cover_art_path AS album_cover_art_path,
            al.artist_id AS album_artist_id,
            al.path AS album_path,
            
            alar.name AS album_artist_name,
            alar.cover_art_path AS album_artist_cover_art_path,
            alar.path AS album_artist_path,

            ar.id AS artist_id,
            ar.name AS artist_name,
            ar.cover_art_path AS artist_cover_art_path,
            ar.path AS artist_path

        FROM records r
        JOIN albums al ON r.album_id = al.id
        JOIN artists ar ON r.artist_id = ar.id
        JOIN artists alar ON al.artist_id = alar.id
    """
    )
    suspend fun getAllRecordsWithAlbumAndArtist(): List<RecordWithAlbumAndArtistEntity>

    @Query("DELETE FROM records")
    suspend fun clear()
}