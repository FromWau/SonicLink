package at.tfro.sonic_link.core.database.converter

import androidx.room.TypeConverter
import kotlinx.io.files.Path

class PathConverter {
    @TypeConverter
    fun fromPath(path: Path): String = path.toString()

    @TypeConverter
    fun toPath(pathString: String): Path = Path(pathString)
}