package at.tfro.sonic_link.shared_client.core.domain.repository

import at.tfro.sonic_link.shared_client.core.domain.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun upsert(setting: Setting)

    fun getAllSettings(): Flow<List<Setting>>

    suspend fun getActiveSetting(): Flow<Setting?>

    suspend fun delete(setting: Setting)
}