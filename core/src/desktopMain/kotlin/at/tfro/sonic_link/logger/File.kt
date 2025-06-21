package at.tfro.sonic_link.logger

import java.io.File

fun getAppDataDir(): File {
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")

    val appDataDir = when {
        os.contains("win") -> File(System.getenv("APPDATA"), "SonicLink")
        os.contains("mac") -> File(userHome, "Library/Application Support/SonicLink")
        else -> File("$userHome/.local/share/SonicLink")
    }

    return appDataDir
}

fun File.createNewFileIfNotExists(): Boolean {
    if (!this.exists()) {
        this.parentFile?.mkdirs()
        return this.createNewFile()
    }
    return false
}
