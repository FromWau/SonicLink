package at.tfro.sonic_link.importer.data.network

import at.tfro.sonic_link.core.domain.repository.SettingRepository
import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.firstOrNull

class ImporterApiClient(
    private val httpClient: HttpClient,
    private val settingRepository: SettingRepository,
) {
    suspend fun getAllImportableMedia(): List<PossibleMediaDto> {
        val setting = settingRepository.getActiveSetting().firstOrNull() // TODO: is firstOrNull() the right choice here?
        if (setting == null) {
            println("Setting not found, cannot fetch importable media.")
            return emptyList()
        }

        println("Fetching importable media from the server...")
        return try {
            val response = httpClient.get {
                url("${setting.host}/triage")
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