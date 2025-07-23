package at.tfro.sonic_link.server.sync.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import at.tfro.sonic_link.core.database.converter.InstantConverter
import at.tfro.sonic_link.core.database.converter.LocaleDateTimeConverter
import at.tfro.sonic_link.core.database.converter.UuidConverter
import at.tfro.sonic_link.server.ServerSettings
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistDao
import at.tfro.sonic_link.server.sync.data.database.dao.GlobalChangeLogDao
import at.tfro.sonic_link.server.sync.data.model.RecordChangeLogEntity
import at.tfro.sonic_link.server.sync.data.database.dao.RecordChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.RecordDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.model.AlbumChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumEntity
import at.tfro.sonic_link.server.sync.data.model.ArtistChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.ArtistEntity
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import kotlinx.coroutines.Dispatchers
import java.io.File

@Database(
    entities = [
        SyncVersionEntity::class, GlobalChangeLogEntity::class,
        ArtistEntity::class, ArtistChangeLogEntity::class,
        AlbumEntity::class, AlbumChangeLogEntity::class,
        RecordEntity::class, RecordChangeLogEntity::class,
    ],
    version = SyncDatabase.DB_VERSION,
)
@TypeConverters(
    UuidConverter::class,
    LocaleDateTimeConverter::class,
    InstantConverter::class,
)
abstract class SyncDatabase : RoomDatabase() {
    abstract fun syncVersionDao(): SyncVersionDao
    abstract fun globalChangeLogDao(): GlobalChangeLogDao

    abstract fun artistDao(): ArtistDao
    abstract fun artistChangeLogDao(): ArtistChangeLogDao

    abstract fun albumDao(): AlbumDao
    abstract fun albumChangeLogDao(): AlbumChangeLogDao

    abstract fun recordDao(): RecordDao
    abstract fun recordChangeLogDao(): RecordChangeLogDao

    companion object {
        const val DB_NAME = "sync"
        const val DB_VERSION = 1
    }
}

private fun getSyncDatabaseBuilder(): RoomDatabase.Builder<SyncDatabase> {
    val dbFile = File(
        ServerSettings().dataDir,
        "${SyncDatabase.DB_NAME}.db"
    )

    return Room.databaseBuilder(dbFile.absolutePath)
}

fun getSyncDatabase(): SyncDatabase = getSyncDatabaseBuilder()
    .fallbackToDestructiveMigration(dropAllTables = true)
    .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()