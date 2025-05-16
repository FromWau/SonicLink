package at.tfro.sonic_link.db.tables

import org.jetbrains.exposed.sql.Table

object MediaArtist : Table("media_artist") {
    val media = reference("media_id", Media.id)
    val artist = reference("artist_id", Artist.id)

    override val primaryKey = PrimaryKey(media, artist)
}
