package at.tfro.sonic_link.server.api.routes.triage

import at.tfro.sonic_link.server.api.routes.triage.model.Artist
import at.tfro.sonic_link.server.api.routes.triage.model.Media
import at.tfro.sonic_link.server.api.routes.triage.model.PossibleMappings
import at.tfro.sonic_link.server.api.routes.triage.model.Recording
import at.tfro.sonic_link.server.features.importer.Importer
import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.server.api.routes.triage.model.ArtistCredit
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

private const val LOG_TAG = "TriageRoutes"

fun Route.triageRoutes() {
    val importer by inject<Importer>()
    val musicbrainzApi by inject<MusicbrainzApi>()
    val logger by inject<Logger>()

    get("/triage") {
        val foundMedia = importer.importAble().toList()

        call.respond(HttpStatusCode.OK, foundMedia)
    }

    post("/triage/import") {
        val request = call.receive<Media>()

        // Validate the request
        if (!importer.exists(request)) {
            logger.tag(LOG_TAG).w { "Requested media path does not exist" }
            call.respond(HttpStatusCode.BadRequest, "Media path does not exist")
            return@post
        }

        val possibleMappings: PossibleMappings =
            runBlocking {
                val mappings = musicbrainzApi.search(
                    recording = request.title,
                    album = request.album,
                    artist = request.artist,
                ).recordings.map {
                    Recording(
                        id = it.id,
                        score = it.score,
                        artistCreditId = it.artistCreditId,
                        title = it.title,
                        disambiguation = it.disambiguation,
                        video = it.video,
                        artistCredit = it.artistCredit.map { ac ->
                            ArtistCredit(
                                name = ac.name,
                                artist = Artist(
                                    id = ac.artist.id,
                                    name = ac.artist.name,
                                    sortName = ac.artist.sortName,
                                    disambiguation = ac.artist.disambiguation
                                )
                            )
                        }
                    )
                }

                PossibleMappings(request, mappings)
            }

        call.respond(HttpStatusCode.OK, possibleMappings)
    }
}
