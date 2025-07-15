package at.tfro.sonic_link.server.api.routes

import at.tfro.sonic_link.server.api.routes.triage.triageRoutes
import at.tfro.sonic_link.core.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.server.sync.routing.syncRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val musicbrainzApi by inject<MusicbrainzApi>()

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "OK")
        }
        triageRoutes()
        streamRoutes()
        mediaRoutes()
        syncRoutes()

        get("/search") {
            val artist = call.request.queryParameters["artist"]
            val album = call.request.queryParameters["album"]
            val recording = call.request.queryParameters["recording"]

            if (artist.isNullOrEmpty() && album.isNullOrEmpty() && recording.isNullOrEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "At least one search parameter must be provided."
                )
                return@get
            }

            val search = musicbrainzApi.search(
                artist = artist,
                album = album,
                recording = recording
            )
            call.respond(HttpStatusCode.OK, search)
        }
    }
}