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
    tableName = "artist_change_log",
    foreignKeys = [
        ForeignKey(
            entity = ArtistEntity::class,
            parentColumns = ["id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["artist_id"]),
        Index(value = ["timestamp"]),
    ],
)
data class ArtistChangeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("artist_id") val artistId: Uuid,
    @ColumnInfo("operation") val operation: DeltaOperationEntity,
    @ColumnInfo("timestamp") val timestamp: Instant,
)

data class ArtistChangeLogWithArtist(
    @Embedded val changeLog: ArtistChangeLogEntity,

    @Relation(
        parentColumn = "artist_id",
        entityColumn = "id",
    )
    val artist: ArtistEntity,
)