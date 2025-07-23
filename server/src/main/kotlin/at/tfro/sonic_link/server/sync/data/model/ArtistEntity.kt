package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(
    tableName = "artists",
    indices = [
        Index(value = ["path"], unique = true),
    ]
)
data class ArtistEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("cover_art_path") val coverArtPath: String?,
    @ColumnInfo("path") val path: String,
)
