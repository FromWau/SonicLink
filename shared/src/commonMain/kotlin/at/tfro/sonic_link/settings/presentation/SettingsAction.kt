package at.tfro.sonic_link.settings.presentation

import at.tfro.sonic_link.core.domain.model.Setting

sealed interface SettingsAction {
    data object OnBack : SettingsAction
    data class OnAddServer(val host: String) : SettingsAction
    data class OnRemoveServer(val setting: Setting) : SettingsAction
    data class OnSelectServer(val setting: Setting) : SettingsAction
    data class OnDeselectServer(val setting: Setting) : SettingsAction
    data class OnUpdateServer(val setting: Setting) : SettingsAction
}