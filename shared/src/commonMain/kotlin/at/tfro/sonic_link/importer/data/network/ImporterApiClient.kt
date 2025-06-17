package at.tfro.sonic_link.importer.data.network

import at.tfro.sonic_link.core.data.network.ensureHttps
import at.tfro.sonic_link.core.domain.repository.SettingRepository
import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import at.tfro.sonic_link.utils.Logger
import at.tfro.sonic_link.utils.e
import at.tfro.sonic_link.utils.tag
import at.tfro.sonic_link.utils.w
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.firstOrNull

class ImporterApiClient(
    private val httpClient: HttpClient,
    private val settingRepository: SettingRepository,
    private val appLogger: Logger,
) {
    companion object {
        private const val LOG_TAG = "ImporterApiClient"
    }

    suspend fun getAllImportableMedia(): List<PossibleMediaDto> {
        // TODO: is firstOrNull() the right choice here?
        val setting = settingRepository.getActiveSetting().firstOrNull()
        if (setting == null) {
            appLogger.tag(LOG_TAG).w { "Setting not found, cannot fetch importable media." }
            return emptyList()
        }

        try {
            val response = httpClient.get {
                url("${setting.host.ensureHttps()}/triage")
            }

            if (response.status.isSuccess()) {
                return response.body<List<PossibleMediaDto>>()
            } else {
                appLogger.tag(LOG_TAG).e { "Error fetching importable media: ${response.status}" }
                return emptyList()
            }
        } catch (e: Exception) {
            appLogger.tag(LOG_TAG).e(e)
            return emptyList()
        }
    }
}