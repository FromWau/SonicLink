package at.tfro.sonic_link.server.db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.tfro.sonic_link.interim.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Entity(tableName = "album")
data class AlbumDbo(
    @PrimaryKey
    @Serializable(with = UuidSerializer::class)
    @ColumnInfo(name = "id") val id: Uuid = Uuid.random(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String,
)