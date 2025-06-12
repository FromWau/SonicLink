package at.tfro.sonic_link.core.data.data_source

import at.tfro.sonic_link.core.data.model.SettingsDto

interface SettingsDataSource {
    suspend fun getSettings(): SettingsDto?
}