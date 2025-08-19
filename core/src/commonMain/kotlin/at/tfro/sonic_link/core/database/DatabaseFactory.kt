package at.tfro.sonic_link.core.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    inline fun <reified T : RoomDatabase> create(dbname: String): RoomDatabase.Builder<T>
}