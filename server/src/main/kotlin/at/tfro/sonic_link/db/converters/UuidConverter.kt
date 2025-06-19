package at.tfro.sonic_link.db.converters

import androidx.room.TypeConverter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UuidConverter {
    @OptIn(ExperimentalUuidApi::class)
    @TypeConverter
    fun fromUuid(uuid: Uuid?): String? {
        return uuid?.toString()
    }

    @OptIn(ExperimentalUuidApi::class)
    @TypeConverter
    fun toUuid(uuidString: String?): Uuid? {
        return uuidString?.let(Uuid.Companion::parse)
    }
}