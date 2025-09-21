package at.tfro.sonic_link.shared_client.core.data.database.media

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlin.uuid.Uuid

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    suspend fun getAllMedia(): List<MediaEntity>

    @Query("DELETE FROM media")
    suspend fun deleteMedia()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(vararg media: MediaEntity)

    @Query("SELECT * FROM assets")
    suspend fun getAllAssets(): List<AssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssets(vararg asset: AssetEntity)

    @Query("DELETE FROM assets")
    suspend fun deleteAssets()

    @Transaction
    @Query(" SELECT * FROM media")
    suspend fun getAllMediaWithAssets(): List<MediaWithAssets>

    @Transaction
    @Query(
        """
       SELECT * FROM media
       WHERE uuid NOT IN (:exclude)
       ORDER BY RANDOM() LIMIT 1
    """
    )
    suspend fun getRandomMedia(exclude: List<Uuid> = emptyList()): MediaWithAssets?
}