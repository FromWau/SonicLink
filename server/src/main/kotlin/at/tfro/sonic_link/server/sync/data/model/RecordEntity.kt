package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(
    tableName = "records",
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["album_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ArtistEntity::class,
            parentColumns = ["id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["album_id"]),
        Index(value = ["artist_id"]),
        Index(value = ["path"], unique = true),
    ],
)
data class RecordEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("album_id") val albumId: Uuid,
    @ColumnInfo("artist_id") val artistId: Uuid,
    @ColumnInfo("path") val path: String,
)


data class RecordWithRelations(
    // Record
    @ColumnInfo(name = "record_id") val recordId: Uuid,
    @ColumnInfo(name = "record_title") val recordTitle: String,
    @ColumnInfo(name = "record_path") val recordPath: String,

    // Album
    @ColumnInfo(name = "album_id") val albumId: Uuid,
    @ColumnInfo(name = "album_title") val albumTitle: String,
    @ColumnInfo(name = "album_cover_art_path") val albumCoverArtPath: String?,
    @ColumnInfo(name = "album_artist_id") val albumArtistId: Uuid,
    @ColumnInfo(name = "album_path") val albumPath: String,

    // Album Artist
    @ColumnInfo(name = "album_artist_name") val albumArtistName: String,
    @ColumnInfo(name = "album_artist_cover_art_path") val albumArtistCoverArtPath: String?,
    @ColumnInfo(name = "album_artist_path") val albumArtistPath: String,

    // Artist
    @ColumnInfo(name = "artist_id") val artistId: Uuid,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "artist_cover_art_path") val artistCoverArtPath: String?,
    @ColumnInfo(name = "artist_path") val artistPath: String,
)
