package at.tfro.sonic_link.server.sync.domain.model

import kotlin.uuid.Uuid

data class Artist(
    val id: Uuid,
    val name: String,
    val coverArtPath: String?,
    val path: String,
) {
    fun toPrettyString(preSpacing: String = ""): String {
        return "Artist {\n" +
                "$preSpacing  id=$id,\n" +
                "$preSpacing  name=$name,\n" +
                "$preSpacing  coverArtPath=$coverArtPath,\n" +
                "$preSpacing  path=$path\n" +
                "$preSpacing}"
    }
}
