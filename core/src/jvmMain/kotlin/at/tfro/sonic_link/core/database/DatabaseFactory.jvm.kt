package at.tfro.sonic_link.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import at.tfro.sonic_link.core.FileFactory
import java.io.File

actual class DatabaseFactory(
    val fileFactory: FileFactory,
) {
    actual inline fun <reified T : RoomDatabase> create(dbname: String): RoomDatabase.Builder<T> {
        val appDataDir = File(fileFactory.appDir())

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, dbname)
        return Room.databaseBuilder(
            name = dbFile.absolutePath,
        )
    }
}
