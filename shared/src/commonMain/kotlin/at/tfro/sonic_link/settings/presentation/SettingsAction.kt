package at.tfro.sonic_link.settings.presentation

sealed interface SettingsAction {
    data object OnBack : SettingsAction
    data class OnServerSettingsChanged(val baseUrl: String) : SettingsAction
}