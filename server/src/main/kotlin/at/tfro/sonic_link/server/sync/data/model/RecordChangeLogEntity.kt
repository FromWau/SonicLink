package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlin.uuid.Uuid

@Entity(
    tableName = "record_change_log",
    foreignKeys = [
        ForeignKey(
            entity = RecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["record_id"],
            onDelete = ForeignKey.Companion.CASCADE,
        )
    ],
    indices = [
        Index(value = ["record_id"]),
        Index(value = ["timestamp"]),
    ],
)
data class RecordChangeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("record_id") val recordId: Uuid,
    @ColumnInfo("operation") val operation: DeltaOperationEntity,
    @ColumnInfo("timestamp") val timestamp: Instant,
)