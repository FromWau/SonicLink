package at.tfro.sonic_link.shared_client.core.data.database.media

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import at.tfro.sonic_link.core.database.converter.UuidConverter
import at.tfro.sonic_link.shared_client.core.data.database.converter.AssetTypeConverter

@Database(
    entities = [MediaEntity::class, AssetEntity::class],
    version = MediaDatabase.DB_VERSION,
)
@TypeConverters(UuidConverter::class, AssetTypeConverter::class)
abstract class MediaDatabase : RoomDatabase() {
    abstract val mediaDao: MediaDao

    companion object {
        const val DB_NAME = "media.db"
        const val DB_VERSION = 1
    }
}

@Suppress(
    "NO_ACTUAL_FOR_EXPECT", // Room creates the actual implementation
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
expect object MediaDatabaseConstructor : RoomDatabaseConstructor<MediaDatabase> {
    override fun initialize(): MediaDatabase
}
