package at.tfro.sonic_link.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<SettingDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(SettingDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}