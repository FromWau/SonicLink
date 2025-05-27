package at.tfro.sonic_link.features.musicbrainz_api

import at.tfro.sonic_link.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object MusicbrainzApi {
    private const val BASE_URL = "https://musicbrainz.org/ws/2/recording"
    private val client = HttpClient(OkHttp) {
        engine {
            config {
                retryOnConnectionFailure(true)
            }
        }
    }

    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun search(
        recording: String? = null,
        album: String? = null,
        artist: String? = null,
    ): SearchResponse {
        if (recording.isNullOrEmpty() && album.isNullOrEmpty() && artist.isNullOrEmpty()) {
            throw IllegalArgumentException("At least one search parameter must be provided.")
        }

        val query = buildString {
            if (!recording.isNullOrEmpty()) append("recording:$recording ")
            if (!album.isNullOrEmpty()) append("release:$album ")
            if (!artist.isNullOrEmpty()) append("artist:$artist ")
        }.trim()

        val response = client.get(BASE_URL) {
            url {
                parameters.append("query", query)
                parameters.append("fmt", "json")
            }
        }

        val body: SearchResponse = response.body<String>().let {
            try {
                return@let json.decodeFromString<SearchResponse>(it)
            } catch (e: Exception) {
                Log.e { "Failed to parse response: ${e.message}" }
                throw e
            }
        }
        Log.d { "Response: $body" }
        return body
    }
}

@Serializable
data class SearchResponse(
    val created: String,
    val count: Int,
    val offset: Int,
    val recordings: List<Recording>,
)

@Serializable
data class Recording(
    val id: String,
    val score: Int,
    @SerialName("artist-credit-id")
    val artistCreditId: String,
    val title: String,
    val disambiguation: String? = null,
    val video: String? = null,
    @SerialName("artist-credit")
    val artistCredit: List<ArtistCredit>,
)

@Serializable
data class ArtistCredit(
    val name: String,
    val artist: Artist,
)

@Serializable
data class Artist(
    val id: String,
    val name: String,
    @SerialName("sort-name")
    val sortName: String? = null,
    val disambiguation: String? = null,
)
