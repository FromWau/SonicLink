package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.data.MediaRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Route.mediaRoutes(mediaFolder: String) {
    get("/media") {
        println("GET /media")
        val mediaRepo by inject<MediaRepository>()
        println("Got media repo")
        val medias = mediaRepo.getAllMedia().map { it.toString() }

//            val medias = listOf(Media(id = UUID.randomUUID(), name = "Test", path = "/test/path", mediaType = Media.MediaType.AUDIO))
        println("Got ${medias.size} media items")
        call.respond(HttpStatusCode.OK, medias)
        println("Responded with ${medias.size} media items")
    }
}