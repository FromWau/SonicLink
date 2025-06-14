package at.tfro.sonic_link.core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "NO_ACTUAL_FOR_EXPECT" // Room creates the actual implementation
)
expect object SettingDatabaseConstructor : RoomDatabaseConstructor<SettingDatabase> {
    override fun initialize(): SettingDatabase
}