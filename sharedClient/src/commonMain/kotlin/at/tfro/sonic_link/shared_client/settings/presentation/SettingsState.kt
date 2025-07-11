package at.tfro.sonic_link.shared_client.settings.presentation

import at.tfro.sonic_link.shared_client.core.domain.model.Setting

data class SettingsState(
    val settings: List<Setting> = emptyList(),
)