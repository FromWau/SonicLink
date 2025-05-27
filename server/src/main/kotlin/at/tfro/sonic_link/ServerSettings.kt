package at.tfro.sonic_link

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import org.slf4j.helpers.Reporter.warn
import java.io.File

object ServerSettings {
    private val config: Config = ConfigFactory.load()

    val libraryFolder: File
        get() {
            val libraryPath = config.propertyOrNull("ktor.media.library.folder")
                ?: System.getenv("LIBRARY_FOLDER")
                ?: error("No library folder specified. Set ktor.media.library.folder in the config or set LIBRARY_FOLDER environment variable.")

            val libraryFolder = File(libraryPath)

            if (!libraryFolder.exists()) {
                warn("Media folder does not exist: $libraryPath - creating it")
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
                warn("Triage folder does not exist: $triagePath - creating it")
                triageFolder.mkdirs()
            }

            return triageFolder
        }
}

private fun Config.propertyOrNull(path: String): String? = try {
    this.getString(path)
} catch (e: ConfigException.Missing) {
    Log.i { "Warning: Config key '$path' exists but is not a string." }
    null
}