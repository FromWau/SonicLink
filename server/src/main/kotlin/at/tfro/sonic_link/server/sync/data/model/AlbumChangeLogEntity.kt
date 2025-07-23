package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.Instant
import kotlin.uuid.Uuid

@Entity(
    tableName = "album_change_log",
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["album_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["album_id"]),
        Index(value = ["timestamp"]),
    ],
)
data class AlbumChangeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("album_id") val albumId: Uuid,
    @ColumnInfo("operation") val operation: DeltaOperationEntity,
    @ColumnInfo("timestamp") val timestamp: Instant,
)

data class AlbumChangeLogWithAlbum(
    @Embedded val changeLog: AlbumChangeLogEntity,

    @Relation(
        parentColumn = "album_id",
        entityColumn = "id",
    )
    val album: AlbumEntity,
)