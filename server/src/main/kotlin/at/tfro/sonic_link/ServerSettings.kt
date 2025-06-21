package at.tfro.sonic_link

import at.tfro.sonic_link.logger.Logger
import at.tfro.sonic_link.logger.tag
import at.tfro.sonic_link.logger.w
import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import java.io.File

class ServerSettings(
    private val logger: Logger,
) {
    companion object {
        private const val LOG_TAG = "ServerSettings"
    }

    private val config: Config get() = ConfigFactory.load()

    val libraryFolder: File
        get() {
            val libraryPath = config.propertyOrNull("ktor.media.library.folder")
                ?: System.getenv("LIBRARY_FOLDER")
                ?: error("No library folder specified. Set ktor.media.library.folder in the config or set LIBRARY_FOLDER environment variable.")

            val libraryFolder = File(libraryPath)

            if (!libraryFolder.exists()) {
                logger.tag(LOG_TAG)
                    .w { "Media folder does not exist: $libraryPath - creating it" }
                libraryFolder.mkdirs()
            }

            return libraryFolder
        }

    val triageFolder: File
        get() {
            val triagePath = config.propertyOrNull("ktor.media.triage.folder")
                ?: System.getenv("TRIAGE_FOLDER")
                ?: error("No triage folder specified. Set ktor.media.triage.folder in the config or set TRIAGE_FOLDER environment variable.")

            val triageFolder = File(triagePath)

            if (!triageFolder.exists()) {
                logger.tag(LOG_TAG).w { "Triage folder does not exist: $triagePath - creating it" }
                triageFolder.mkdirs()
            }

            return triageFolder
        }


    private fun Config.propertyOrNull(path: String): String? = try {
        this.getString(path)
    } catch (_: ConfigException.Missing) {
        logger.tag(LOG_TAG).w { "Config key '$path' exists but is not a string." }
        null
    }
}
