package at.tfro.sonic_link.core.data.model

data class SettingsDto(val serverSettings: ServerSettings) {
    data class ServerSettings(val baseUrl: String)
}
