package at.tfro.sonic_link.core.data.model

data class SettingDto(val serverSettings: ServerSettings) {
    data class ServerSettings(val baseUrl: String)
}
