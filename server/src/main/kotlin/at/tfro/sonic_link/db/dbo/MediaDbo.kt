package at.tfro.sonic_link.db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "media")
data class MediaDbo(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "type") val mediaType: MediaType,
) {
    enum class MediaType { AUDIO, VIDEO, }
}