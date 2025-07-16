package at.tfro.sonic_link.core.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class LocaleDateTimeConverter {
    @TypeConverter
    fun fromLocaleDateTime(ldt: LocalDateTime) = ldt.toString()

    @TypeConverter
    fun toLocaleDateTime(value: String) = LocalDateTime.parse(value)
}