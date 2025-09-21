package at.tfro.sonic_link.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import at.tfro.sonic_link.core.SystemAppDirectories

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseFactory(
    val dirs: SystemAppDirectories,
    val context: Context,
) {
    actual inline fun <reified T : RoomDatabase> create(
        dbname: String,
    ): RoomDatabase.Builder<T> {
        val dbFile = dirs.databaseFile(dbname)

        return Room.databaseBuilder(
            context = context.applicationContext,
            name = dbFile.toString(),
        )
    }
}
