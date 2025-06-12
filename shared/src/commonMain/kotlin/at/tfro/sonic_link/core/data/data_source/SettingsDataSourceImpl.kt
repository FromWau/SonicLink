package at.tfro.sonic_link.core.data.data_source

import at.tfro.sonic_link.core.data.model.SettingsDto

class SettingsDataSourceImpl : SettingsDataSource {
    override suspend fun getSettings(): SettingsDto? {
        TODO("Get settings from room")
    }
}