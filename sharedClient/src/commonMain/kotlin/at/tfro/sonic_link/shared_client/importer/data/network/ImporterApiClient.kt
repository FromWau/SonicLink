package at.tfro.sonic_link.shared_client.importer.data.network

import at.tfro.sonic_link.core.logger.Log
import at.tfro.sonic_link.core.network.ensureProtocol
import at.tfro.sonic_link.shared_client.core.domain.repository.SettingRepository
import at.tfro.sonic_link.shared_client.importer.data.model.PossibleMediaDto
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
    companion object {
        private const val TAG = "ImporterApiClient"
    }

    suspend fun getAllImportableMedia(): List<PossibleMediaDto> {
        // TODO: is firstOrNull() the right choice here?
        val setting = settingRepository.getActiveSetting().firstOrNull()
        if (setting == null) {
            Log.tag(TAG).w { "Setting not found, cannot fetch importable media." }
            return emptyList()
        }

        try {
            val response = httpClient.get {
                url("${setting.host.ensureProtocol()}/triage")
            }

            if (response.status.isSuccess()) {
                return response.body<List<PossibleMediaDto>>()
            } else {
                Log.tag(TAG).e { "Error fetching importable media: ${response.status}" }
                return emptyList()
            }
        } catch (e: Exception) {
            Log.tag(TAG).e(e)
            return emptyList()
        }
    }
}