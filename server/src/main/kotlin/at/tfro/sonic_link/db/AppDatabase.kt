package at.tfro.sonic_link.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import at.tfro.sonic_link.db.dao.AlbumDao
import at.tfro.sonic_link.db.dao.ArtistDao
import at.tfro.sonic_link.db.dao.MediaDao
import at.tfro.sonic_link.db.dbo.AlbumDbo
import at.tfro.sonic_link.db.dbo.ArtistDbo
import at.tfro.sonic_link.db.dbo.MediaDbo
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
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun artistDao(): ArtistDao
    abstract fun albumDao(): AlbumDao
}

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(
        System.getProperty("java.io.tmpdir"),
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