package at.tfro.sonic_link.shared_client.core.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey val id: Uuid,
    val host: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = false,
)