package at.tfro.sonic_link.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.tfro.sonic_link.database.converter.UuidConverter

@Database(
    entities = [SettingEntity::class],
    version = 1,
)
@TypeConverters(UuidConverter::class)
abstract class SettingDatabase : RoomDatabase() {
    abstract val settingDao: SettingDao

    companion object {
        const val DB_NAME = "setting.db"
    }
}