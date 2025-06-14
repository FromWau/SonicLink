package at.tfro.sonic_link.core.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SettingDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")

        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "SonicLink")
            os.contains("mac") -> File(userHome, "Library/Application Support/SonicLink")
            else -> File("$userHome/.local/share/SonicLink")
        }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, SettingDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}