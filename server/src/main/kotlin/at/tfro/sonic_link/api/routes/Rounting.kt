package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.features.musicbrainz_api.MusicbrainzApi
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "OK")
        }
        triageRoutes()
        streamRoutes()
        mediaRoutes()

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

            val search = MusicbrainzApi.search(
                artist = artist,
                album = album,
                recording = recording
            )
            call.respond(HttpStatusCode.OK, search)
        }
    }
}