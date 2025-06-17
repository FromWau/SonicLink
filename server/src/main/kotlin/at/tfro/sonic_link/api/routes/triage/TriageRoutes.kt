package at.tfro.sonic_link.api.routes.triage

import at.tfro.sonic_link.Log
import at.tfro.sonic_link.api.routes.triage.model.Media
import at.tfro.sonic_link.api.routes.triage.model.PossibleMappings
import at.tfro.sonic_link.features.importer.Importer
import at.tfro.sonic_link.features.musicbrainz_api.MusicbrainzApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.coroutines.runBlocking

fun Route.triageRoutes() {
    get("/triage") {
        val foundMedia = Importer().importAble().toList()

        call.respond(HttpStatusCode.OK, foundMedia)
    }

    post("/triage/import") {
        val request = call.receive<Media>()

        // Validate the request
        if (!Importer().exists(request)) {
            Log.w { "Requested media path does not exist" }
            call.respond(HttpStatusCode.BadRequest, "Media path does not exist")
            return@post
        }

        val possibleMappings: PossibleMappings =
            runBlocking {
                val mappings = MusicbrainzApi.search(
                    recording = request.title,
                    album = request.album,
                    artist = request.artist,
                ).recordings

                PossibleMappings(request, mappings)
            }

        call.respond(HttpStatusCode.OK, possibleMappings)
    }
}

