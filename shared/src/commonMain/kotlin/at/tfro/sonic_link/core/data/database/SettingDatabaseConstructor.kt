package at.tfro.sonic_link.core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT") // Room creates the actual implementation
expect object SettingDatabaseConstructor : RoomDatabaseConstructor<SettingDatabase> {
    override fun initialize(): SettingDatabase
}