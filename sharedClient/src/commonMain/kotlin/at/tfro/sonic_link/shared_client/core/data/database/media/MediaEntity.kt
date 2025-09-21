package at.tfro.sonic_link.shared_client.core.data.database.media

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: Uuid,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "album") val album: String,
)
