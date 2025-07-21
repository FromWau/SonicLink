package at.tfro.sonic_link.server.sync.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.Uuid

data class Record(
    val id: Uuid,
    val title: String,
    val album: Album,
    val artist: Artist,
    val path: String,

    val syncVersion: SyncVersion,
    val lastModified: LocalDateTime,
    val isDeleted: Boolean,
) {
    fun toPrettyString(preSpacing: String = ""): String {
        return "Record {\n" +
                "$preSpacing  id=$id,\n" +
                "$preSpacing  title=$title,\n" +
                "$preSpacing  album=${album.toPrettyString("$preSpacing  ")},\n" +
                "$preSpacing  artist=${artist.toPrettyString("$preSpacing  ")},\n" +
                "$preSpacing  path=$path\n" +
                "$preSpacing  syncVersion=${syncVersion.toPrettyString("$preSpacing  ")},\n" +
                "$preSpacing  lastModified=$lastModified,\n" +
                "$preSpacing  isDeleted=$isDeleted\n" +
                "$preSpacing}"
    }
}