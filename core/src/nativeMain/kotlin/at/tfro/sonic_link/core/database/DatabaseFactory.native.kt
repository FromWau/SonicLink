package at.tfro.sonic_link.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import at.tfro.sonic_link.core.FileFactory

actual class DatabaseFactory(
    val fileFactory: FileFactory,
) {
    actual inline fun <reified T : RoomDatabase> create(dbname: String): RoomDatabase.Builder<T> {
        val dbFile = fileFactory.appDir() + "/${dbname}"
        return Room.databaseBuilder(
            name = dbFile,
        )
    }
}
