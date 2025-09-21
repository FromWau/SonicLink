package at.tfro.sonic_link.shared_client.core.data.database.setting

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import at.tfro.sonic_link.core.database.converter.UuidConverter

@Database(
    entities = [SettingEntity::class],
    version = SettingDatabase.DB_VERSION,
)
@TypeConverters(UuidConverter::class)
abstract class SettingDatabase : RoomDatabase() {
    abstract val settingDao: SettingDao

    companion object {
        const val DB_NAME = "setting.db"
        const val DB_VERSION = 1
    }
}

@Suppress(
    "NO_ACTUAL_FOR_EXPECT", // Room creates the actual implementation
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
expect object SettingDatabaseConstructor : RoomDatabaseConstructor<SettingDatabase> {
    override fun initialize(): SettingDatabase
}
