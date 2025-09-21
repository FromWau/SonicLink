package at.tfro.sonic_link.shared_client.core.data.database.media

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import at.tfro.sonic_link.core.fileExtension
import kotlinx.io.files.Path
import kotlin.uuid.Uuid

@Entity(
    tableName = "assets",
    foreignKeys = [
        ForeignKey(
            entity = MediaEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["media_uuid"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class AssetEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: Uuid,
    @ColumnInfo(name = "media_uuid") val mediaUuid: Uuid,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "type") val type: AssetType,
)

enum class AssetType {
    AUDIO,
    VIDEO,
    IMAGE,
    LYRIC,
    OTHER,
    ;

    companion object {
        fun fromFileExtension(file: Path): AssetType = when (file.fileExtension) {
            "mp3", "flac", "wav", "m4a", "aac", "ogg" -> AUDIO
            "mp4", "mkv", "avi", "mov", "wmv" -> VIDEO
            "jpg", "jpeg", "png", "gif", "bmp" -> IMAGE
            "lrc", "txt" -> LYRIC
            else -> OTHER
        }
    }
}
