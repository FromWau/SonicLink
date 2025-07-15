package at.tfro.sonic_link.shared_client.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
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