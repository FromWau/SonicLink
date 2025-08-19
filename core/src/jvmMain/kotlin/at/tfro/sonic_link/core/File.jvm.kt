package at.tfro.sonic_link.core

import java.io.File

actual class FileFactory {
    actual fun appDir(): String {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDirName = "eventportal"

        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), appDataDirName)
            os.contains("mac") -> File(userHome, "Library/Application Support/$appDataDirName")
            else -> File("$userHome/.local/share/$appDataDirName")
        }

        return appDataDir.absolutePath
    }
}
