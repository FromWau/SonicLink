package at.tfro.sonic_link.importer.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.isSuccess

class ImporterApiClient(
    private val httpClient: HttpClient,
) {
    suspend fun getAllImportableMedia(): List<String> {
        println("Fetching importable media from the server...")
        return try {
            val response = httpClient.get {
                url("http://localhost:8080/triage")
            }

            if (response.status.isSuccess()) {
                return response.body()
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