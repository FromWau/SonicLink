package at.tfro.sonic_link.core.domain.model

data class SettingsModel(val serverSettings: ServerSettings) {
    data class ServerSettings(val baseUrl: String)
}