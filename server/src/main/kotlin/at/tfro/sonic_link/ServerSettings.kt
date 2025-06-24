package at.tfro.sonic_link

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import java.io.File

class ServerSettings() {
    private val config: Config get() = ConfigFactory.load()

    val libraryFolder: File
        get() {
            val libraryPath = config.propertyOrNull("ktor.media.library.folder")
                ?: System.getenv("LIBRARY_FOLDER")
                ?: error("No library folder specified. Set ktor.media.library.folder in the config or set LIBRARY_FOLDER environment variable.")

            val libraryFolder = File(libraryPath)

            if (!libraryFolder.exists()) {
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
                triageFolder.mkdirs()
            }

            return triageFolder
        }

    val dataDir: File
        get() {
            val dataPath = config.propertyOrNull("data.dir")
                ?: System.getenv("DATA_DIR")
                ?: error("No data directory specified. Set data.dir in the config or set DATA_DIR environment variable.")

            val dataDir = File(dataPath)

            if (!dataDir.exists()) {
                dataDir.mkdirs()
            }

            return dataDir
        }


    private fun Config.propertyOrNull(path: String): String? = try {
        this.getString(path)
    } catch (_: ConfigException.Missing) {
        null
    }
}
