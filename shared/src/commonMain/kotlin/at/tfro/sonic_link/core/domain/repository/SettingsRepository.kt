package at.tfro.sonic_link.core.domain.repository

import at.tfro.sonic_link.core.domain.model.SettingsModel

interface SettingsRepository {
    suspend fun getSettings(): SettingsModel?
}