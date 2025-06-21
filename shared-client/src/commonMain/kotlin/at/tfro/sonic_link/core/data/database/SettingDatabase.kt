package at.tfro.sonic_link.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SettingEntity::class],
    version = 1,
)
abstract class SettingDatabase : RoomDatabase() {
    abstract val settingDao: SettingDao

    companion object {
        const val DB_NAME = "setting.db"
    }
}