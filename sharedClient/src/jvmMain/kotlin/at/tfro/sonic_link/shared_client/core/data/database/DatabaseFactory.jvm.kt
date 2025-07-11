package at.tfro.sonic_link.shared_client.core.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import at.tfro.sonic_link.core.logger.getAppDataDir
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SettingDatabase> {
        val appDataDir = getAppDataDir()

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, SettingDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}