package at.tfro.sonic_link.server.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import at.tfro.sonic_link.server.ServerSettings
import at.tfro.sonic_link.core.database.converter.UuidConverter
import at.tfro.sonic_link.server.db.dao.AlbumDao
import at.tfro.sonic_link.server.db.dao.ArtistDao
import at.tfro.sonic_link.server.db.dao.MediaDao
import at.tfro.sonic_link.server.db.dbo.AlbumDbo
import at.tfro.sonic_link.server.db.dbo.ArtistDbo
import at.tfro.sonic_link.server.db.dbo.MediaDbo
import kotlinx.coroutines.Dispatchers
import java.io.File

@Database(
    entities = [
        AlbumDbo::class,
        ArtistDbo::class,
        MediaDbo::class,
    ],
    version = DatabaseConfig.APP_DATABASE_VERSION,
)
@TypeConverters(UuidConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun artistDao(): ArtistDao
    abstract fun albumDao(): AlbumDao
}

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(
        ServerSettings().dataDir,
        "${DatabaseConfig.APP_DATABASE_NAME}.db"
    )

    return Room.databaseBuilder(dbFile.absolutePath)
}

fun getDatabase(): AppDatabase = getDatabaseBuilder()
    .fallbackToDestructiveMigration(dropAllTables = true)
    .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()