package at.tfro.sonic_link.core.data.database

import androidx.room.RoomDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SettingDatabase>
}