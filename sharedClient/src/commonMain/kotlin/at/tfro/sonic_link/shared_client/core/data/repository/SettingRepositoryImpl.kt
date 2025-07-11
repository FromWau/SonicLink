package at.tfro.sonic_link.shared_client.core.data.repository

import at.tfro.sonic_link.shared_client.core.data.database.SettingDao
import at.tfro.sonic_link.shared_client.core.data.mapper.toDomain
import at.tfro.sonic_link.shared_client.core.data.mapper.toSettingEntity
import at.tfro.sonic_link.shared_client.core.domain.model.Setting
import at.tfro.sonic_link.shared_client.core.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val dao: SettingDao,
) : SettingRepository {
    override suspend fun upsert(setting: Setting) =
        dao.upsertSingleActive(setting.toSettingEntity())

    override fun getAllSettings(): Flow<List<Setting>> =
        dao.getAllSettings().map {
            it.map { settingEntity -> settingEntity.toDomain() }
        }

    override suspend fun getActiveSetting(): Flow<Setting?> =
        dao.getActiveSetting().map { it?.toDomain() }

    override suspend fun delete(setting: Setting) =
        dao.delete(setting.toSettingEntity())
}