package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.features.importer.Importer
import at.tfro.sonic_link.features.importer.TriageMedia
import at.tfro.sonic_link.features.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.features.musicbrainz_api.Recording
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

fun Route.triageRoutes() {
    get("/triage") {
        val foundMedia = Importer().importAble().toList()

        call.respond(HttpStatusCode.OK, foundMedia)
    }

    get("/triage/import") {
        val possibleMappings: List<PossibleMappings> = Importer().importAble()
            .map { triageMedia ->
                runBlocking {
                    val mappings = MusicbrainzApi.search(
                        recording = triageMedia.possibleTitle,
                        album = triageMedia.possibleAlbum,
                        artist = triageMedia.possibleArtist
                    ).recordings

                    PossibleMappings(triageMedia, mappings)
                }
            }
            .toList()

        call.respond(HttpStatusCode.OK, possibleMappings)
    }
}

@Serializable
data class PossibleMappings(
    val triageMedia: TriageMedia,
    val mappings: List<Recording>,
)

@Serializable
data class MappingRequest(
    val triageMedia: TriageMedia,
    val recording: Recording,
)
