package at.tfro.sonic_link.shared_client.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {
    // INFO: DO NOT USE @Upsert, this breaks desktop platform
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settings: SettingEntity)

    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingEntity>>

    @Query("SELECT * FROM settings WHERE is_active = 1 LIMIT 1")
    fun getActiveSetting(): Flow<SettingEntity?>

    @Delete
    suspend fun delete(setting: SettingEntity)

    @Query("UPDATE settings SET is_active = 0 WHERE is_active = 1")
    suspend fun deactivateAll()

    @Transaction
    suspend fun upsertSingleActive(setting: SettingEntity) {
        if (setting.isActive) {
            deactivateAll()
        }
        upsert(setting)
    }
}