package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.TriageFile
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.io.File

fun Route.triageRoutes(triageFolder: String) {
    get("/triage") {
        val triageFiles: List<String> = File(triageFolder).walk()
            .filter { it.isFile }
            .map {
                val relativePath = it.relativeTo(File(triageFolder)).path
                val mediaType = it.extension
                TriageFile(it.name, relativePath, mediaType).toString()
            }
            .toList()

        call.respond(HttpStatusCode.OK, triageFiles)
    }
}