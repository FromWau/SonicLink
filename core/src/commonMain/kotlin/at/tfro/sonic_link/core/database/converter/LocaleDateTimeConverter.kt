package at.tfro.sonic_link.core.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class LocaleDateTimeConverter {
    @TypeConverter
    fun fromLocaleDateTime(localeDateTime: LocalDateTime): Long =
        localeDateTime.toInstant(TimeZone.UTC).epochSeconds

    @TypeConverter
    fun toLocaleDateTime(epochMillis: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(TimeZone.UTC)
}