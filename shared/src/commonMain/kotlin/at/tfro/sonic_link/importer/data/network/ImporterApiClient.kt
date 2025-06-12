package at.tfro.sonic_link.importer.data.network

import at.tfro.sonic_link.core.domain.repository.SettingsRepository
import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.isSuccess

class ImporterApiClient(
    private val httpClient: HttpClient,
    private val settingsRepository: SettingsRepository,
) {
    suspend fun getAllImportableMedia(): List<PossibleMediaDto> {
        val settings = settingsRepository.getSettings()
        if (settings == null) {
            println("Settings not found, cannot fetch importable media.")
            return emptyList()
        }

        println("Fetching importable media from the server...")
        return try {
            val response = httpClient.get {
                url("${settings.serverSettings.baseUrl}/triage")
            }

            if (response.status.isSuccess()) {
                return response.body<List<PossibleMediaDto>>()
            } else {
                println("Error fetching importable media: ${response.status}")
                return emptyList()
            }
        } catch (e: Exception) {
            println("Network error: ${e.message}")
            emptyList()
        }
    }
}