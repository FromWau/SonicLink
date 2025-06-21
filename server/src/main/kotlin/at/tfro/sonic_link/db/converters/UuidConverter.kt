package at.tfro.sonic_link.db.converters

import androidx.room.TypeConverter
import kotlin.uuid.Uuid

class UuidConverter {
    @TypeConverter
    fun fromUuid(uuid: Uuid?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUuid(uuidString: String?): Uuid? {
        return uuidString?.let(Uuid.Companion::parse)
    }
}