package at.tfro.sonic_link.shared_client.core.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SettingDatabase>
}