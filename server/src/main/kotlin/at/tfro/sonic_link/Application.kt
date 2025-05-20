package at.tfro.sonic_link

import at.tfro.sonic_link.api.plugins.configureKoin
import at.tfro.sonic_link.api.plugins.configureSerialization
import at.tfro.sonic_link.api.plugins.configureTrailingSlashRedirect
import at.tfro.sonic_link.api.routes.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.netty.EngineMain
import java.io.File

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
    configureKoin()
    configureSerialization()
    configureTrailingSlashRedirect()

    val config = this.engine.environment.config
    val mediaFolder = config.propertyOrNull("ktor.media.library.folder")?.getString()
        ?: System.getenv("LIBRARY_FOLDER")
        ?: error("No library folder specified. Set ktor.media.folder in the config or set LIBRARY_FOLDER environment variable.")
    if (!File(mediaFolder).exists()) {
        log.warn("Media folder does not exist: $mediaFolder - creating it")
        File(mediaFolder).mkdirs()
    }

    val triageFolder = config.propertyOrNull("ktor.media.triage.folder")?.getString()
        ?: System.getenv("TRIAGE_FOLDER")
        ?: error("No triage folder specified. Set ktor.media.triage.folder in the config or set TRIAGE_FOLDER environment variable.")
    if (!File(triageFolder).exists()) {
        log.warn("Triage folder does not exist: $triageFolder - creating it")
        File(triageFolder).mkdirs()
    }

    configureRouting(
        mediaFolder = mediaFolder,
        triageFolder = triageFolder,
    )
}

data class TriageFile(
    val name: String,
    val path: String,
    val type: String,
)