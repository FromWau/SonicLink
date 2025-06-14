package at.tfro.sonic_link.core.data.repository

import at.tfro.sonic_link.core.data.database.SettingDao
import at.tfro.sonic_link.core.data.mapper.toDomain
import at.tfro.sonic_link.core.data.mapper.toSettingEntity
import at.tfro.sonic_link.core.domain.model.Setting
import at.tfro.sonic_link.core.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val dao: SettingDao,
) : SettingRepository {
    override suspend fun upsert(setting: Setting) =
        dao.upsert(setting.toSettingEntity())

    override fun getAllSettings(): Flow<List<Setting>> =
        dao.getAllSettings().map {
            it.map { settingEntity -> settingEntity.toDomain() }
        }

    override suspend fun getActiveSetting(): Flow<Setting?> =
        dao.getActiveSetting().map { it?.toDomain() }

    override suspend fun getSettingById(id: Long): Setting? =
        dao.getSettingById(id)?.toDomain()

    override suspend fun deleteSettingById(id: Long) =
        dao.deleteSettingById(id)
}