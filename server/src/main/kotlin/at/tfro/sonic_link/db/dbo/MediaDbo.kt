package at.tfro.sonic_link.db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.tfro.sonic_link.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Entity(tableName = "media")
data class MediaDbo(
    @PrimaryKey
    @Serializable(with = UuidSerializer::class)
    @ColumnInfo(name = "id") val id: Uuid = Uuid.random(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "type") val mediaType: MediaType,
) {
    enum class MediaType { AUDIO, VIDEO, }
}