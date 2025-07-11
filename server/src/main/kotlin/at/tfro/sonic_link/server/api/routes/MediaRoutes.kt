package at.tfro.sonic_link.server.api.routes

import at.tfro.sonic_link.server.data.MediaRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Route.mediaRoutes() {
    get("/media") {
        val mediaRepo by inject<MediaRepository>()
        val medias = mediaRepo.getAllMedia().map { it.toString() }

        call.respond(HttpStatusCode.OK, medias)
    }
}