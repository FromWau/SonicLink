package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.Log
import at.tfro.sonic_link.features.importer.ImportMedia
import at.tfro.sonic_link.features.importer.Importer
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.slf4j.helpers.Reporter

fun Route.triageRoutes() {
    get("/triage") {
        val foundMedia = Importer().importAble().toList()

        call.respond(HttpStatusCode.OK, foundMedia)
    }

    get("/triage/import") {
        val media = ImportMedia(
            path = "sample_artist/sample_album/sample.mp3",
            type = ImportMedia.MediaType.AUDIO,
            title = "Test Title",
            artist = "Test Artist",
            album = "Test Album"
        )
        Log.i { "Build media object to be requested for import" }
        Importer().import(media)

        call.respond(HttpStatusCode.OK)
    }
}