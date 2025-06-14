package at.tfro.sonic_link.settings.presentation

import at.tfro.sonic_link.core.domain.model.Setting

data class SettingsState(
    val settings: List<Setting> = emptyList(),
)