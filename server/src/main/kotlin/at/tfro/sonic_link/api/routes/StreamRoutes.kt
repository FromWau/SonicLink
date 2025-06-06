package at.tfro.sonic_link.api.routes

import at.tfro.sonic_link.ServerSettings
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFile
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.io.File

fun Route.streamRoutes() {
    get("/stream/{filename}") {
        val filename = call.parameters["filename"]
            ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing filename")
        val file = File(ServerSettings.libraryFolder, filename)

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