package at.tfro.sonic_link.core.data.repository

import at.tfro.sonic_link.core.data.data_source.SettingsDataSource
import at.tfro.sonic_link.core.domain.model.SettingsModel
import at.tfro.sonic_link.core.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource,
) : SettingsRepository {
    override suspend fun getSettings(): SettingsModel? {
        return settingsDataSource.getSettings().let {
            TODO("Convert SettingsDto to SettingsModel")
        }
    }
}