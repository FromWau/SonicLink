package at.tfro.sonic_link.server.sync.domain.model

import kotlin.uuid.Uuid

data class Album(
    val id: Uuid,
    val title: String,
    val artist: Artist,
    val coverArtPath: String?,
    val path: String,
) {
    fun toPrettyString(preSpacing: String = ""): String {
        return "Album {\n" +
                "$preSpacing  id=$id,\n" +
                "$preSpacing  title=$title,\n" +
                "$preSpacing  artist=${artist.toPrettyString("$preSpacing  ")},\n" +
                "$preSpacing  coverArtPath=$coverArtPath,\n" +
                "$preSpacing  path=$path\n" +
                "$preSpacing}"
    }
}