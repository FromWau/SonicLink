package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlin.uuid.Uuid

@Entity(tableName = "global_change_log")
data class GlobalChangeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("version") val version: Long,
    @ColumnInfo("model") val model: String,
    @ColumnInfo("entity_id") val entityId: Uuid,
    @ColumnInfo("operation") val operation: DeltaOperationEntity,
    @ColumnInfo("timestamp") val timestamp: Instant,
)
