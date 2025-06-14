package at.tfro.sonic_link.settings.presentation

sealed interface SettingsAction {
    data object OnBack : SettingsAction
    data class OnAddServer(val baseUrl: String) : SettingsAction
    data class OnRemoveServer(val serverSettings: SettingsState.ServerSettings) : SettingsAction
}