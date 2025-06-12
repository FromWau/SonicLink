package at.tfro.sonic_link.settings.presentation

data class SettingsState(
    val serverSettings: ServerSettings = ServerSettings(baseUrl = "")
) {
    data class ServerSettings(val baseUrl: String)
}