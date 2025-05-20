package at.tfro.sonic_link.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting(mediaFolder: String, triageFolder: String) {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "OK")
        }
        triageRoutes(triageFolder)
        streamRoutes(mediaFolder)
        mediaRoutes(mediaFolder)
    }
}