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
    tableName = "artists",
    foreignKeys = [
        ForeignKey(
            entity = SyncVersionEntity::class,
            parentColumns = ["version"],
            childColumns = ["sync_version_version"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["path"], unique = true),
        Index(value = ["sync_version_version"]),
    ]
)
data class ArtistEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Uuid,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("cover_art_path") val coverArtPath: String?,
    @ColumnInfo("path") val path: String,

    // Sync metadata:
    @ColumnInfo("sync_version_version") val syncVersionVersion: Long,
    @ColumnInfo("last_modified") val lastModified: LocalDateTime,
    @ColumnInfo("is_deleted") val isDeleted: Boolean = false,
)

data class ArtistWithRelations(
    @Embedded val artist: ArtistEntity,

    @Relation(
        parentColumn = "sync_version_version",
        entityColumn = "version"
    )
    val syncVersion: SyncVersionEntity,
)