package at.tfro.sonic_link.server.sync.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "sync_versions")
data class SyncVersionEntity(
    @PrimaryKey(autoGenerate = true) val version: Long = 0L,
    @ColumnInfo(name = "released_at") val releasedAt: LocalDateTime,
)