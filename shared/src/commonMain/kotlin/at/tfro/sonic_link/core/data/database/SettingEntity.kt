package at.tfro.sonic_link.core.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val baseUrl: String,
    val isActive: Boolean = false
)