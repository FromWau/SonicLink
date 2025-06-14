package at.tfro.sonic_link.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {
    @Upsert
    suspend fun upsert(settings: SettingEntity)

    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingEntity>>

    @Query("SELECT * FROM settings WHERE isActive = 1 LIMIT 1")
    fun getActiveSetting(): Flow<SettingEntity?>

    @Query("SELECT * FROM settings WHERE id = :id")
    suspend fun getSettingById(id: Long): SettingEntity?

    @Query("DELETE FROM settings WHERE id = :id")
    suspend fun deleteSettingById(id: Long)
}