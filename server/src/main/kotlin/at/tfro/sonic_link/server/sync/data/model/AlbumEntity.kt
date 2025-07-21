package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.Uuid

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = ArtistEntity::class,
            parentColumns = ["id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = SyncVersionEntity::class,
            parentColumns = ["version"],
            childColumns = ["sync_version_version"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["artist_id"]),
        Index(value = ["sync_version_version"]),
        Index(value = ["path"], unique = true),
    ],
)
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("artist_id") val artistId: Uuid,
    @ColumnInfo("cover_art_path") val coverArtPath: String?,
    @ColumnInfo("path") val path: String,

    // Sync metadata:
    @ColumnInfo("sync_version_version") val syncVersionVersion: Long,
    @ColumnInfo("last_modified") val lastModified: LocalDateTime,
    @ColumnInfo("is_deleted") val isDeleted: Boolean = false,
)

data class AlbumWithRelations(
    @Embedded val album: AlbumEntity,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id"
    )
    val artist: ArtistEntity,

    @Relation(
        parentColumn = "sync_version_version",
        entityColumn = "version"
    )
    val syncVersion: SyncVersionEntity,
)
