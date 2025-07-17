package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlin.uuid.Uuid

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = ArtistEntity::class,
            parentColumns = ["id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["artist_id"]),
        Index(value = ["path"], unique = true)
    ],
)
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("artist_id") val artistId: Uuid,
    @ColumnInfo("cover_art_path") val coverArtPath: String?,
    @ColumnInfo("path") val path: String,
)

data class AlbumWithArtistEntity(
    @Embedded val album: AlbumEntity,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: ArtistEntity,
)
