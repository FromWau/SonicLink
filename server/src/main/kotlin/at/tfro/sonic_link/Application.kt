package at.tfro.sonic_link

import at.tfro.sonic_link.db.DatabaseFactory
import at.tfro.sonic_link.db.dao.ArtistDAO
import at.tfro.sonic_link.db.dao.MediaDAO
import at.tfro.sonic_link.db.dao.MediaDTO
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFile
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

fun main(args: Array<String>) {
    EngineMain.main(args)

    DatabaseFactory.initH2()
    ArtistDAO.create(
        name = "Toto",
        path = "media/toto/",
        art = null,
    )

    MediaDAO.create(
        name = "Afrika",
        path = "media/africa.mp3",
        mediaType = MediaDTO.MediaType.AUDIO,
        albumId = null,
        artistIds = listOf(1),
    )
}


fun Application.module() {
    val config = this.engine.environment.config
    val mediaFolder = config.propertyOrNull("ktor.media.folder")?.getString()
        ?: System.getenv("MEDIA_FOLDER")
        ?: error("No media folder specified. Set ktor.media.folder in the config or set MEDIA_FOLDER environment variable.")

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "OK")
        }

        get("/media/{filename}") {
            val filename = call.parameters["filename"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing filename")
            val file = File(mediaFolder, filename)

            if (!file.exists()) {
                return@get call.respond(HttpStatusCode.NotFound, "File not found")
            }

            val rangeHeader = call.request.headers["Range"]
            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                val (startStr, endStr) = rangeHeader.removePrefix("bytes=").split("-").let {
                    it[0] to it.getOrNull(1)
                }

                val fileSize = file.length()
                val start = startStr.toLong()
                val end = endStr?.toLong() ?: (fileSize - 1)
                val contentLength = end - start + 1

                call.response.status(HttpStatusCode.PartialContent)
                call.response.header(HttpHeaders.AcceptRanges, "bytes")
                call.response.header(HttpHeaders.ContentLength, contentLength.toString())
                call.response.header(HttpHeaders.ContentRange, "bytes $start-$end/$fileSize")

                call.respondOutputStream(contentType = ContentType.defaultForFile(file)) {
                    file.inputStream().use {
                        it.skip(start)
                        it.copyTo(this, contentLength.toInt())
                    }
                }
            } else {
                call.respondFile(file)
            }
        }
    }
}