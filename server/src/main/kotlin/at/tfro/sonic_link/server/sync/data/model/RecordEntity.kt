package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
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
        ForeignKey(
            entity = SyncVersionEntity::class,
            parentColumns = ["version"],
            childColumns = ["sync_version_version"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["album_id"]),
        Index(value = ["artist_id"]),
        Index(value = ["path"], unique = true),
        Index(value = ["sync_version_version"]),
    ],
)
data class RecordEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("album_id") val albumId: Uuid,
    @ColumnInfo("artist_id") val artistId: Uuid,
    @ColumnInfo("path") val path: String,

    // Sync metadata:
    @ColumnInfo("sync_version_version") val syncVersionVersion: Long,
    @ColumnInfo("last_modified") val lastModified: LocalDateTime,
    @ColumnInfo("is_deleted") val isDeleted: Boolean = false,
)


data class RecordWithRelations(
    // Record
    @ColumnInfo(name = "record_id") val recordId: Uuid,
    @ColumnInfo(name = "record_title") val recordTitle: String,
    @ColumnInfo(name = "record_path") val recordPath: String,
    @ColumnInfo(name = "record_sync_version_version") val recordSyncVersionVersion: Long,
    @ColumnInfo(name = "record_sync_version_released_at") val recordSyncVersionReleasedAt: LocalDateTime,
    @ColumnInfo(name = "record_last_modified") val recordLastModified: LocalDateTime,
    @ColumnInfo(name = "record_is_deleted") val recordIsDeleted: Boolean,

    // Album
    @ColumnInfo(name = "album_id") val albumId: Uuid,
    @ColumnInfo(name = "album_title") val albumTitle: String,
    @ColumnInfo(name = "album_cover_art_path") val albumCoverArtPath: String?,
    @ColumnInfo(name = "album_artist_id") val albumArtistId: Uuid,
    @ColumnInfo(name = "album_path") val albumPath: String,
    @ColumnInfo(name = "album_sync_version_version") val albumSyncVersionVersion: Long,
    @ColumnInfo(name = "album_sync_version_released_at") val albumSyncVersionReleasedAt: LocalDateTime,
    @ColumnInfo(name = "album_last_modified") val albumLastModified: LocalDateTime,
    @ColumnInfo(name = "album_is_deleted") val albumIsDeleted: Boolean,

    // Album Artist
    @ColumnInfo(name = "album_artist_name") val albumArtistName: String,
    @ColumnInfo(name = "album_artist_cover_art_path") val albumArtistCoverArtPath: String?,
    @ColumnInfo(name = "album_artist_path") val albumArtistPath: String,
    @ColumnInfo(name = "album_artist_sync_version_version") val albumArtistSyncVersionVersion: Long,
    @ColumnInfo("album_artist_sync_version_released_at") val albumArtistSyncVersionReleasedAt: LocalDateTime,
    @ColumnInfo(name = "album_artist_last_modified") val albumArtistLastModified: LocalDateTime,
    @ColumnInfo(name = "album_artist_is_deleted") val albumArtistIsDeleted: Boolean,

    // Artist
    @ColumnInfo(name = "artist_id") val artistId: Uuid,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "artist_cover_art_path") val artistCoverArtPath: String?,
    @ColumnInfo(name = "artist_path") val artistPath: String,
    @ColumnInfo(name = "artist_sync_version_version") val artistSyncVersionVersion: Long,
    @ColumnInfo(name = "artist_sync_version_released_at") val artistSyncVersionReleasedAt: LocalDateTime,
    @ColumnInfo(name = "artist_last_modified") val artistLastModified: LocalDateTime,
    @ColumnInfo(name = "artist_is_deleted") val artistIsDeleted: Boolean,
)
